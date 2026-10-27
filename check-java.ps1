$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_课程工具\resolve-jdk.ps1')
$jdk = Find-CourseJdk -Root $PSScriptRoot
Write-Output "Course JDK: $jdk"
& (Join-Path $jdk 'bin\java.exe') -version
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& (Join-Path $jdk 'bin\javac.exe') -version
exit $LASTEXITCODE
