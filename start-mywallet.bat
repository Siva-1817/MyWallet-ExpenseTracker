
@echo off
title MyWallet Launcher

cd /d "%~dp0"

echo Starting MyWallet...

echo Starting Spring Boot backend...
start "MyWallet Backend" cmd /k "cd /d "%~dp0backend" && mvnw.cmd spring-boot:run"

timeout /t 5 /nobreak >nul

echo Starting React frontend...
start "MyWallet Frontend" cmd /k "cd /d "%~dp0frontend" && npm start"

timeout /t 10 /nobreak >nul
start "" http://localhost:3000

echo MyWallet startup commands have been launched.
pause
