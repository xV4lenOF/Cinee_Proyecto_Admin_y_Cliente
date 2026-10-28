@echo off
setlocal
cd /d "%~dp0"
where mvn >nul 2>nul
if errorlevel 1 (
  echo No se encontro Maven. Abre CineeAdmin como proyecto Maven desde NetBeans, o instala Maven.
  pause
  exit /b 1
)
where java >nul 2>nul
if errorlevel 1 (
  echo No se encontro Java en PATH. Instala/configura JDK 17.
  pause
  exit /b 1
)
echo Construyendo Cinee Admin...
call mvn -q clean package
if errorlevel 1 (
  echo La construccion fallo. Revisa el registro de Maven.
  pause
  exit /b 1
)
echo Abriendo Cinee Admin...
java -jar target\cinee-admin-1.0.0.jar
endlocal
