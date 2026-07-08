# Builds Geasd (Fabric) using the installed Java 25 JDK.
$jdkHome = "C:\Program Files\Microsoft\jdk-25.0.3.9-hotspot"
if (-not (Test-Path "$jdkHome\bin\java.exe")) {
    Write-Error "Java 25 not found at $jdkHome. Install with: winget install Microsoft.OpenJDK.25 --accept-source-agreements --disable-interactivity"
    exit 1
}

$env:JAVA_HOME = $jdkHome
$env:Path = "$jdkHome\bin;$env:Path"

Set-Location $PSScriptRoot
& .\gradlew.bat @args
