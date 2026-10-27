[CmdletBinding()]
param(
    [Parameter(Mandatory=$true, Position=0)][string]$Source,
    [string[]]$ProgramArgs = @(),
    [switch]$CompileOnly
)
$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_课程工具\resolve-jdk.ps1')
$jdk = Find-CourseJdk -Root $PSScriptRoot
$sourcePath = (Resolve-Path -LiteralPath $Source).Path
if ([IO.Path]::GetExtension($sourcePath) -ne '.java') { throw 'Source must be a .java file.' }
$className = [IO.Path]::GetFileNameWithoutExtension($sourcePath)
$outputPath = Join-Path $PSScriptRoot ('.build\run-' + [guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $outputPath -Force | Out-Null
$oldOutputEncoding = [Console]::OutputEncoding
$oldInputEncoding = [Console]::InputEncoding
try {
    [Console]::OutputEncoding = [Text.UTF8Encoding]::new($false)
    [Console]::InputEncoding = [Text.UTF8Encoding]::new($false)
    # Fresh output directory prevents an old .class file from hiding a compile failure.
    & (Join-Path $jdk 'bin\javac.exe') '-J-Dfile.encoding=UTF-8' '-J-Dstdout.encoding=UTF-8' '-J-Dstderr.encoding=UTF-8' --release 8 -encoding UTF-8 '-Xlint:all,-options' -d $outputPath $sourcePath
    $programExitCode = $LASTEXITCODE
    if ($programExitCode -eq 0) {
        if ($CompileOnly) {
            Write-Output "Compilation succeeded: $outputPath"
        } else {
            & (Join-Path $jdk 'bin\java.exe') '-Dfile.encoding=UTF-8' '-Dstdout.encoding=UTF-8' '-Dstderr.encoding=UTF-8' -cp $outputPath $className @ProgramArgs
            $programExitCode = $LASTEXITCODE
        }
    }
} finally {
    [Console]::OutputEncoding = $oldOutputEncoding
    [Console]::InputEncoding = $oldInputEncoding
}
exit $programExitCode
