# Start Both Backend and Frontend
# Services will stop when you close the terminal or service windows

param(
    [switch]$SkipBackend,
    [switch]$SkipFrontend
)

# Get the project root directory (parent of scripts folder)
$scriptPath = $PSScriptRoot
if (-not $scriptPath) {
    $scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
}
if (-not $scriptPath) {
    $scriptPath = Get-Location
}

# Navigate to project root (one level up from scripts folder)
$projectRoot = Split-Path -Parent $scriptPath
Set-Location $projectRoot

Write-Host ""
Write-Host "================================================================" -ForegroundColor Cyan
Write-Host "   Starting Spring Banking System" -ForegroundColor Cyan
Write-Host "================================================================" -ForegroundColor Cyan
Write-Host ""

# Check if we're in the right directory
if (-not (Test-Path "pom.xml")) {
    Write-Host "ERROR: pom.xml not found!" -ForegroundColor Red
    Write-Host "Please run this script from the project root directory." -ForegroundColor Yellow
    exit 1
}

# Check if ports are already in use
Write-Host "Checking port availability..." -ForegroundColor Gray
$port8080InUse = $false
$port3000InUse = $false

try {
    $conn8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
    if ($conn8080) {
        $port8080InUse = $true
        $pid8080 = $conn8080.OwningProcess | Select-Object -First 1 -Unique
        Write-Host "  WARNING: Port 8080 is already in use (PID: $pid8080)" -ForegroundColor Yellow
    }
}
catch { }

try {
    $conn3000 = Get-NetTCPConnection -LocalPort 3000 -ErrorAction SilentlyContinue
    if ($conn3000) {
        $port3000InUse = $true
        $pid3000 = $conn3000.OwningProcess | Select-Object -First 1 -Unique
        Write-Host "  WARNING: Port 3000 is already in use (PID: $pid3000)" -ForegroundColor Yellow
    }
}
catch { }

if ($port8080InUse -or $port3000InUse) {
    Write-Host ""
    $response = Read-Host "Do you want to stop existing services and continue? (Y/N)"
    if ($response -ne "Y" -and $response -ne "y") {
        Write-Host "Aborted. Run .\scripts\stop-services.ps1 first, then try again." -ForegroundColor Yellow
        exit 1
    }
    else {
        Write-Host "Stopping existing services..." -ForegroundColor Yellow
        & "$projectRoot\scripts\stop-services.ps1"
        Start-Sleep -Seconds 2
    }
}

Write-Host ""

# Cleanup function
function Stop-AllServices {
    Write-Host ""
    Write-Host "Cleaning up services..." -ForegroundColor Yellow
    
    # Stop backend
    try {
        $conn = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
        if ($conn) {
            $pid = $conn.OwningProcess | Select-Object -First 1 -Unique
            Stop-Process -Id $pid -Force -ErrorAction SilentlyContinue
            Write-Host "  Backend stopped (port 8080)" -ForegroundColor Green
        }
    }
    catch { }
    
    # Stop frontend
    try {
        $conn = Get-NetTCPConnection -LocalPort 3000 -ErrorAction SilentlyContinue
        if ($conn) {
            $pid = $conn.OwningProcess | Select-Object -First 1 -Unique
            Stop-Process -Id $pid -Force -ErrorAction SilentlyContinue
            Write-Host "  Frontend stopped (port 3000)" -ForegroundColor Green
        }
    }
    catch { }
}

# Register cleanup on script exit
Register-EngineEvent PowerShell.Exiting -Action { Stop-AllServices } | Out-Null

# Start Backend
if (-not $SkipBackend) {
    Write-Host "Starting Backend (Spring Boot)..." -ForegroundColor Cyan
    
    # Create temporary script for backend with cleanup
    $backendScript = @"
`$Host.UI.RawUI.WindowTitle = 'Spring Banking - Backend'
cd '$projectRoot'

# Cleanup function
function Cleanup {
    Write-Host ''
    Write-Host 'Stopping backend...' -ForegroundColor Yellow
    try {
        `$conn = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
        if (`$conn) {
            `$pid = `$conn.OwningProcess | Select-Object -First 1 -Unique
            Stop-Process -Id `$pid -Force -ErrorAction SilentlyContinue
        }
    }
    catch { }
    Write-Host 'Backend stopped. Port 8080 is free.' -ForegroundColor Green
}

# Register cleanup
Register-EngineEvent PowerShell.Exiting -Action { Cleanup } | Out-Null

Write-Host '========================================' -ForegroundColor Green
Write-Host '   Spring Boot Backend' -ForegroundColor Green
Write-Host '========================================' -ForegroundColor Green
Write-Host 'Backend: http://localhost:8080' -ForegroundColor Yellow
Write-Host 'Closing this window stops the backend' -ForegroundColor Cyan
Write-Host ''
.\mvnw.cmd spring-boot:run
"@
    
    $backendScriptFile = Join-Path $env:TEMP "springbanking-backend.ps1"
    $backendScript | Out-File -FilePath $backendScriptFile -Encoding UTF8
    
    try {
        $backendProcess = Start-Process powershell.exe -ArgumentList "-NoExit", "-ExecutionPolicy", "Bypass", "-File", "`"$backendScriptFile`"" -PassThru
        Write-Host "  Backend window opened (PID: $($backendProcess.Id))" -ForegroundColor Green
        Write-Host "  Closing that window will stop the backend" -ForegroundColor Gray
        
        # Wait for backend to initialize
        Write-Host "  Waiting for backend to initialize (5 seconds)..." -ForegroundColor Gray
        Start-Sleep -Seconds 5
    }
    catch {
        Write-Host "ERROR: Failed to start backend: $_" -ForegroundColor Red
    }
}
else {
    Write-Host "Skipping backend (SkipBackend flag set)" -ForegroundColor Yellow
}

# Start Frontend
if (-not $SkipFrontend) {
    Write-Host ""
    Write-Host "Starting Frontend (React)..." -ForegroundColor Cyan
    
    $frontendDir = Join-Path $projectRoot "frontend"
    
    if (-not (Test-Path $frontendDir)) {
        Write-Host "ERROR: Frontend directory not found!" -ForegroundColor Red
        Stop-AllServices
        exit 1
    }
    
    # Check and install dependencies
    $nodeModulesPath = Join-Path $frontendDir "node_modules"
    if (-not (Test-Path $nodeModulesPath)) {
        Write-Host "  Installing frontend dependencies..." -ForegroundColor Yellow
        Push-Location $frontendDir
        npm install --silent
        Pop-Location
    }
    
    # Check and create .env file
    $envFile = Join-Path $frontendDir ".env"
    if (-not (Test-Path $envFile)) {
        Write-Host "  Creating .env file..." -ForegroundColor Yellow
        $envContent = @"
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_NAME=Spring Banking System
"@
        $envContent | Out-File -FilePath $envFile -Encoding UTF8
    }
    
    # Create temporary script for frontend with cleanup
    $frontendScript = @"
`$Host.UI.RawUI.WindowTitle = 'Spring Banking - Frontend'
cd '$frontendDir'

# Cleanup function
function Cleanup {
    Write-Host ''
    Write-Host 'Stopping frontend...' -ForegroundColor Yellow
    try {
        `$conn = Get-NetTCPConnection -LocalPort 3000 -ErrorAction SilentlyContinue
        if (`$conn) {
            `$pid = `$conn.OwningProcess | Select-Object -First 1 -Unique
            Stop-Process -Id `$pid -Force -ErrorAction SilentlyContinue
        }
    }
    catch { }
    Write-Host 'Frontend stopped. Port 3000 is free.' -ForegroundColor Green
}

# Register cleanup
Register-EngineEvent PowerShell.Exiting -Action { Cleanup } | Out-Null

Write-Host '========================================' -ForegroundColor Green
Write-Host '   React Frontend' -ForegroundColor Green
Write-Host '========================================' -ForegroundColor Green
Write-Host 'Frontend: http://localhost:3000' -ForegroundColor Yellow
Write-Host 'Closing this window stops the frontend' -ForegroundColor Cyan
Write-Host ''
npm run dev
"@
    
    $frontendScriptFile = Join-Path $env:TEMP "springbanking-frontend.ps1"
    $frontendScript | Out-File -FilePath $frontendScriptFile -Encoding UTF8
    
    try {
        $frontendProcess = Start-Process powershell.exe -ArgumentList "-NoExit", "-ExecutionPolicy", "Bypass", "-File", "`"$frontendScriptFile`"" -PassThru
        Write-Host "  Frontend window opened (PID: $($frontendProcess.Id))" -ForegroundColor Green
        Write-Host "  Closing that window will stop the frontend" -ForegroundColor Gray
    }
    catch {
        Write-Host "ERROR: Failed to start frontend: $_" -ForegroundColor Red
    }
}
else {
    Write-Host "Skipping frontend (SkipFrontend flag set)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "================================================================" -ForegroundColor Green
Write-Host "   Services Started!" -ForegroundColor Green
Write-Host "================================================================" -ForegroundColor Green
Write-Host ""
Write-Host "Access Points:" -ForegroundColor Yellow
Write-Host "   Backend API:  http://localhost:8080/api" -ForegroundColor White
Write-Host "   Frontend:     http://localhost:3000" -ForegroundColor White
Write-Host "   Customer:     http://localhost:3000/login" -ForegroundColor White
Write-Host "   Admin:        http://localhost:3000/admin/login" -ForegroundColor White
Write-Host ""
Write-Host "IMPORTANT:" -ForegroundColor Yellow
Write-Host "  - Closing service windows will stop those services" -ForegroundColor White
Write-Host "  - Closing this terminal will attempt to stop all services" -ForegroundColor White
Write-Host ""
Write-Host "To manually stop: .\scripts\stop-services.ps1" -ForegroundColor Gray
Write-Host ""
