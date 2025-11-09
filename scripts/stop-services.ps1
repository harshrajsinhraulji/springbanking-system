# Stop All Services (Backend and Frontend)
# This script kills processes running on ports 8080 and 3000

Write-Host ""
Write-Host "================================================================" -ForegroundColor Yellow
Write-Host "   Stopping Spring Banking Services" -ForegroundColor Yellow
Write-Host "================================================================" -ForegroundColor Yellow
Write-Host ""

# Function to kill process on a port
function Stop-ProcessOnPort {
    param(
        [int]$Port,
        [string]$ServiceName
    )
    
    Write-Host "Checking port $Port ($ServiceName)..." -ForegroundColor Cyan
    
    try {
        # Get process using the port
        $connection = Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue
        
        if ($connection) {
            $processId = $connection.OwningProcess | Select-Object -First 1 -Unique
            $process = Get-Process -Id $processId -ErrorAction SilentlyContinue
            
            if ($process) {
                Write-Host "  Found process: $($process.ProcessName) (PID: $processId)" -ForegroundColor Yellow
                Write-Host "  Stopping process..." -ForegroundColor Yellow
                
                try {
                    Stop-Process -Id $processId -Force -ErrorAction Stop
                    Write-Host "  Successfully stopped $ServiceName" -ForegroundColor Green
                }
                catch {
                    Write-Host "  ERROR: Could not stop process: $_" -ForegroundColor Red
                }
            }
            else {
                Write-Host "  No process found for PID: $processId" -ForegroundColor Gray
            }
        }
        else {
            Write-Host "  Port $Port is not in use" -ForegroundColor Gray
        }
    }
    catch {
        Write-Host "  Could not check port $Port" -ForegroundColor Red
    }
}

# Stop Backend (Port 8080)
Stop-ProcessOnPort -Port 8080 -ServiceName "Backend (Spring Boot)"

# Stop Frontend (Port 3000)
Stop-ProcessOnPort -Port 3000 -ServiceName "Frontend (React)"

# Also try to kill Java processes (Spring Boot)
Write-Host ""
Write-Host "Checking for Java processes..." -ForegroundColor Cyan
$javaProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue
if ($javaProcesses) {
    Write-Host "  Found $($javaProcesses.Count) Java process(es)" -ForegroundColor Yellow
    foreach ($proc in $javaProcesses) {
        Write-Host "    Stopping: $($proc.ProcessName) (PID: $($proc.Id))" -ForegroundColor Yellow
        try {
            Stop-Process -Id $proc.Id -Force -ErrorAction Stop
            Write-Host "    Stopped successfully" -ForegroundColor Green
        }
        catch {
            Write-Host "    Could not stop: $_" -ForegroundColor Red
        }
    }
}
else {
    Write-Host "  No Java processes found" -ForegroundColor Gray
}

# Also try to kill Node processes (React/Vite)
Write-Host ""
Write-Host "Checking for Node processes..." -ForegroundColor Cyan
$nodeProcesses = Get-Process -Name "node" -ErrorAction SilentlyContinue
if ($nodeProcesses) {
    Write-Host "  Found $($nodeProcesses.Count) Node process(es)" -ForegroundColor Yellow
    foreach ($proc in $nodeProcesses) {
        Write-Host "    Stopping: $($proc.ProcessName) (PID: $($proc.Id))" -ForegroundColor Yellow
        try {
            Stop-Process -Id $proc.Id -Force -ErrorAction Stop
            Write-Host "    Stopped successfully" -ForegroundColor Green
        }
        catch {
            Write-Host "    Could not stop: $_" -ForegroundColor Red
        }
    }
}
else {
    Write-Host "  No Node processes found" -ForegroundColor Gray
}

Write-Host ""
Write-Host "================================================================" -ForegroundColor Green
Write-Host "   Services Stopped" -ForegroundColor Green
Write-Host "================================================================" -ForegroundColor Green
Write-Host ""
Write-Host "You can now run .\scripts\start-all.ps1 to start services again" -ForegroundColor Yellow
Write-Host ""

