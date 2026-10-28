@echo off
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0descargar_mysql_connector.ps1"
if errorlevel 1 pause
