param([switch]$Checks)

$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

$a1JdkCandidates = @((Join-Path $env:USERPROFILE '.jdks\jdk-21.0.13+9'))
if (-not [string]::IsNullOrWhiteSpace($env:JAVA_HOME)) {
    $a1JdkCandidates += $env:JAVA_HOME
}
$a1CompilerOnPath = Get-Command javac.exe -CommandType Application -ErrorAction SilentlyContinue | Select-Object -First 1
if ($null -ne $a1CompilerOnPath) {
    $a1JdkCandidates += Split-Path (Split-Path $a1CompilerOnPath.Source -Parent) -Parent
}
$a1JdkRoot = $null
foreach ($a1Candidate in $a1JdkCandidates) {
    if ((Test-Path -LiteralPath (Join-Path $a1Candidate 'bin\javac.exe')) -and
            (Test-Path -LiteralPath (Join-Path $a1Candidate 'bin\java.exe'))) {
        $a1JdkRoot = $a1Candidate
        break
    }
}
if ($null -eq $a1JdkRoot) {
    throw 'JDK 21 or newer not found. Configure JAVA_HOME or put a JDK on PATH.'
}
$a1Compiler = Join-Path $a1JdkRoot 'bin\javac.exe'
$a1Runtime = Join-Path $a1JdkRoot 'bin\java.exe'

$a1SourceDirectory = Join-Path $PSScriptRoot 'src'
$a1OutputDirectory = Join-Path $PSScriptRoot 'bin'
$a1MainClass = 'AssignmentOne'
$a1SourceFiles = @(Get-ChildItem -LiteralPath $a1SourceDirectory -Filter '*.java' -File | ForEach-Object { $_.FullName })
if ($a1SourceFiles.Count -eq 0) {
    throw 'No Java source files were found in src.'
}
if ($Checks) {
    $a1ChecksDirectory = Join-Path $PSScriptRoot 'checks'
    $a1CheckFiles = @(Get-ChildItem -LiteralPath $a1ChecksDirectory -Filter '*.java' -File | ForEach-Object { $_.FullName })
    if ($a1CheckFiles.Count -eq 0) {
        throw 'No behaviour checks were found in checks.'
    }
    $a1SourceFiles += $a1CheckFiles
    $a1OutputDirectory = Join-Path $PSScriptRoot 'checks-bin'
    $a1MainClass = 'AppointmentSystemChecks'
}

New-Item -ItemType Directory -Path $a1OutputDirectory -Force | Out-Null
& $a1Compiler --release 21 -encoding UTF-8 -Xlint:all -Werror -d $a1OutputDirectory @a1SourceFiles
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

& $a1Runtime '-Dfile.encoding=UTF-8' -cp $a1OutputDirectory $a1MainClass
exit $LASTEXITCODE
