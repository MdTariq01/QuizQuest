# Quiz RPG Build & Run Script

Write-Host "Creating output directory..." -ForegroundColor Cyan
if (!(Test-Path -Path "out")) {
    New-Item -ItemType Directory -Path "out" -Force
}

Write-Host "Compiling project files..." -ForegroundColor Cyan
$javaFiles = Get-ChildItem -Path src\main\java -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d out $javaFiles

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful! Launching QuizQuest..." -ForegroundColor Green
    java -cp out com.quizrpg.QuizQuest
} else {
    Write-Host "Compilation failed. Please check the error messages above." -ForegroundColor Red
}
