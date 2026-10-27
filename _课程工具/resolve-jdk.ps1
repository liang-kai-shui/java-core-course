function Find-CourseJdk {
    param([string]$Root)
    $candidates = @()
    $config = Join-Path $Root '.java-course.json'
    if (Test-Path -LiteralPath $config) {
        $setting = Get-Content -LiteralPath $config -Raw -Encoding UTF8 | ConvertFrom-Json
        if ($setting.jdkHome) { $candidates += $setting.jdkHome }
    }
    if ($env:JAVA_HOME) { $candidates += $env:JAVA_HOME }
    $compiler = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($compiler) { $candidates += Split-Path (Split-Path $compiler.Source -Parent) -Parent }
    foreach ($base in @((Join-Path $env:ProgramFiles 'Java'), (Join-Path $env:ProgramFiles 'Eclipse Adoptium'))) {
        if (Test-Path -LiteralPath $base) {
            $candidates += @(Get-ChildItem -LiteralPath $base -Directory | Sort-Object Name -Descending | ForEach-Object { $_.FullName })
        }
    }
    foreach ($candidate in $candidates) {
        if ((Test-Path -LiteralPath (Join-Path $candidate 'bin\javac.exe')) -and
            (Test-Path -LiteralPath (Join-Path $candidate 'bin\java.exe'))) { return $candidate }
    }
    throw 'No JDK found. Set jdkHome in .java-course.json, or set JAVA_HOME to an installed JDK 17/21.'
}
