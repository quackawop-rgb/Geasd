# Push Geasd source code to GitHub
# Run from PowerShell AFTER installing Git:  right-click -> Run with PowerShell
# Or:  cd "C:\Users\wopid\Desktop\Geasd MC mod"
#       .\push-to-github.ps1

$ErrorActionPreference = "Stop"
$projectRoot = $PSScriptRoot
$remoteUrl = "https://github.com/quackawop-rgb/Geasd.git"

function Find-Git {
    $git = Get-Command git -ErrorAction SilentlyContinue
    if ($git) { return $git.Source }

    $candidates = @(
        "${env:ProgramFiles}\Git\cmd\git.exe",
        "${env:ProgramFiles}\Git\bin\git.exe",
        "${env:ProgramFiles(x86)}\Git\cmd\git.exe"
    )
    foreach ($path in $candidates) {
        if (Test-Path $path) { return $path }
    }
    return $null
}

$gitExe = Find-Git
if (-not $gitExe) {
    Write-Host ""
    Write-Host "Git is not installed." -ForegroundColor Red
    Write-Host ""
    Write-Host "Install it with ONE of these options:" -ForegroundColor Yellow
    Write-Host "  1. winget install Git.Git"
    Write-Host "  2. Download: https://git-scm.com/download/win"
    Write-Host ""
    Write-Host "Then CLOSE and REOPEN PowerShell and run this script again."
    Write-Host ""
    exit 1
}

Write-Host "Using Git: $gitExe" -ForegroundColor Green
Set-Location $projectRoot

function Invoke-Git {
    param([Parameter(ValueFromRemainingArguments = $true)][string[]]$Args)
    & $gitExe @Args
    if ($LASTEXITCODE -ne 0) {
        throw "git $($Args -join ' ') failed with exit code $LASTEXITCODE"
    }
}

if (-not (Test-Path ".git")) {
    Invoke-Git init
}

$remote = & $gitExe remote get-url origin 2>$null
if ($LASTEXITCODE -ne 0) {
    Invoke-Git remote add origin $remoteUrl
} else {
    Write-Host "Remote origin: $remote"
}

Invoke-Git add .
Invoke-Git status

$hasChanges = & $gitExe diff --cached --quiet 2>$null
if ($LASTEXITCODE -ne 0) {
    Invoke-Git commit -m "Add Geasd source code"
} else {
    Write-Host "Nothing new to commit." -ForegroundColor Yellow
}

Invoke-Git branch -M main

Write-Host ""
Write-Host "Pushing to GitHub..." -ForegroundColor Cyan
Write-Host "Sign in if a browser or login window appears." -ForegroundColor Cyan
Write-Host ""

try {
    Invoke-Git push -u origin main
} catch {
    Write-Host ""
    Write-Host "Push failed. If GitHub already has a README, run:" -ForegroundColor Yellow
    Write-Host "  git pull origin main --allow-unrelated-histories"
    Write-Host "  git push -u origin main"
    Write-Host ""
    throw
}

Write-Host ""
Write-Host "Done! Source code should be at:" -ForegroundColor Green
Write-Host "  https://github.com/quackawop-rgb/Geasd"
Write-Host ""
