$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$lib = Join-Path $projectRoot 'lib'
New-Item -ItemType Directory -Force -Path $lib | Out-Null
$url = 'https://repo.maven.apache.org/maven2/com/mysql/mysql-connector-j/9.7.0/mysql-connector-j-9.7.0.jar'
$destination = Join-Path $lib 'mysql-connector-j-9.7.0.jar'
Write-Host 'Descargando el controlador JDBC oficial de MySQL...'
Invoke-WebRequest -Uri $url -OutFile $destination
Write-Host "Listo: $destination"
Write-Host 'Ahora abre Cin_Integrado en NetBeans y ejecuta Clean and Build.'
