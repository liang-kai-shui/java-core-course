[CmdletBinding()]
param(
    [Parameter(Mandatory=$true)][string]$Project,
    [ValidateSet('Run','Jar','List','Build')][string]$Mode = 'Run',
    [string]$SourceRoot = '',
    [string[]]$ProgramArgs = @()
)
$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_课程工具\resolve-jdk.ps1')
$jdk = Find-CourseJdk -Root $PSScriptRoot
$definitions = Get-Content -LiteralPath (Join-Path $PSScriptRoot '_课程工具\projects.json') -Raw -Encoding UTF8 | ConvertFrom-Json
$definition = @($definitions | Where-Object { $_.id -eq $Project })
if ($definition.Count -ne 1) { throw "Unknown project: $Project. See _course project definitions." }
$definition = $definition[0]
$sourceBase = if ($SourceRoot) { (Resolve-Path -LiteralPath $SourceRoot).Path } else { Join-Path $PSScriptRoot $definition.root }
$sourceBase = [IO.Path]::GetFullPath($sourceBase)
if (-not $sourceBase.StartsWith($PSScriptRoot.TrimEnd('\') + '\', [StringComparison]::OrdinalIgnoreCase)) {
    throw 'Project sources must stay inside this course workspace.'
}

function Get-ProjectSource {
    param([string]$Base, [string]$Relative)
    $resolved = [IO.Path]::GetFullPath((Join-Path $Base $Relative))
    if (-not $resolved.StartsWith($Base.TrimEnd('\') + '\', [StringComparison]::OrdinalIgnoreCase)) { throw 'Path leaves project.' }
    if (-not (Test-Path -LiteralPath $resolved -PathType Leaf)) { throw "Missing project input: $Relative" }
    return $resolved
}

function Invoke-ProjectTool {
    param([string]$Executable, [string[]]$Arguments)
    & $Executable @Arguments
    if ($LASTEXITCODE -ne 0) { throw "Tool failed ($LASTEXITCODE); no previous class or JAR will be run." }
}

$buildBase = Join-Path $PSScriptRoot ('.build\project-' + [guid]::NewGuid().ToString('N'))
$classes = Join-Path $buildBase 'classes'
New-Item -ItemType Directory -Path $classes -Force | Out-Null
$compiler = Join-Path $jdk 'bin\javac.exe'
$archiver = Join-Path $jdk 'bin\jar.exe'
$launcher = Join-Path $jdk 'bin\java.exe'
$compileOptions = @('-J-Dfile.encoding=UTF-8','-J-Dstdout.encoding=UTF-8','-J-Dstderr.encoding=UTF-8',
                    '--release','8','-encoding','UTF-8','-Xlint:all,-options')
$runOptions = @('-Dfile.encoding=UTF-8','-Dstdout.encoding=UTF-8','-Dstderr.encoding=UTF-8')
$oldOutputEncoding = [Console]::OutputEncoding
$oldInputEncoding = [Console]::InputEncoding
try {
    [Console]::OutputEncoding = [Text.UTF8Encoding]::new($false)
    [Console]::InputEncoding = [Text.UTF8Encoding]::new($false)
    $libraryJar = ''
    if (@($definition.library_sources).Count -gt 0) {
        $libraryClasses = Join-Path $buildBase 'library-classes'
        $libraryDir = Join-Path $buildBase 'lib'
        New-Item -ItemType Directory -Path $libraryClasses,$libraryDir -Force | Out-Null
        $librarySources = @($definition.library_sources | ForEach-Object { Get-ProjectSource -Base $sourceBase -Relative $_ })
        Invoke-ProjectTool -Executable $compiler -Arguments ($compileOptions + @('-classpath',$libraryClasses,'-d',$libraryClasses) + $librarySources)
        $libraryJar = Join-Path $libraryDir 'labels.jar'
        Invoke-ProjectTool -Executable $archiver -Arguments @('cf',$libraryJar,'-C',$libraryClasses,'.')
    }
    $projectSources = @($definition.sources | ForEach-Object { Get-ProjectSource -Base $sourceBase -Relative $_ })
    $compileClasspath = if ($libraryJar) { $libraryJar } else { $classes }
    Invoke-ProjectTool -Executable $compiler -Arguments ($compileOptions + @('-classpath',$compileClasspath,'-d',$classes) + $projectSources)
    foreach ($resource in @($definition.resources)) {
        $resourceSource = Get-ProjectSource -Base $sourceBase -Relative $resource.source
        $resourceTarget = [IO.Path]::GetFullPath((Join-Path $classes $resource.target))
        if (-not $resourceTarget.StartsWith($classes + '\', [StringComparison]::OrdinalIgnoreCase)) { throw 'Resource leaves output directory.' }
        New-Item -ItemType Directory -Path (Split-Path $resourceTarget -Parent) -Force | Out-Null
        Copy-Item -LiteralPath $resourceSource -Destination $resourceTarget
    }
    $manifest = Join-Path $buildBase 'manifest.mf'
    $manifestLines = @('Manifest-Version: 1.0', ('Main-Class: ' + $definition.main))
    if ($libraryJar) { $manifestLines += 'Class-Path: lib/labels.jar' }
    $manifestLines += ''
    $manifestLines | Set-Content -LiteralPath $manifest -Encoding Ascii
    $applicationJar = Join-Path $buildBase 'app.jar'
    Invoke-ProjectTool -Executable $archiver -Arguments @('cfm',$applicationJar,$manifest,'-C',$classes,'.')
    if ($Mode -eq 'Build') {
        Write-Output "Build: $buildBase"
        Write-Output "Classes: $classes"
        Write-Output "Application: $applicationJar"
        if ($libraryJar) { Write-Output "Library: $libraryJar" }
    } elseif ($Mode -eq 'List') {
        Invoke-ProjectTool -Executable $archiver -Arguments @('tf',$applicationJar)
    } elseif ($Mode -eq 'Jar') {
        Invoke-ProjectTool -Executable $launcher -Arguments ($runOptions + @('-jar',$applicationJar) + $ProgramArgs)
    } else {
        $runtimeClasspath = $classes
        if ($libraryJar) { $runtimeClasspath += [IO.Path]::PathSeparator + $libraryJar }
        Invoke-ProjectTool -Executable $launcher -Arguments ($runOptions + @('-cp',$runtimeClasspath,$definition.main) + $ProgramArgs)
    }
} finally {
    [Console]::OutputEncoding = $oldOutputEncoding
    [Console]::InputEncoding = $oldInputEncoding
}
