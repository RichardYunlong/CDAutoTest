@echo off
chcp 936>nul
setlocal

rem ==========================================================
rem  Third-party jars installer (ASCII only, do not save as UTF-8)
rem  ojdbc6 / AutoItX4Java cannot be downloaded from Maven Central.
rem  Run this after changing machine or cleaning the local repo.
rem  Usage: install-3rd-party-jars.bat [mavenHome]
rem ==========================================================

set "MVN_HOME=D:\BaiduSyncdisk\CDRAGON\tools\apache-maven-3.2.5"

rem MAVEN_HOME may point to a stale path, only trust it when mvn.bat really exists
if not "%MAVEN_HOME%"=="" (
    if exist "%MAVEN_HOME%\bin\mvn.bat" set "MVN_HOME=%MAVEN_HOME%"
)

if not "%~1"=="" set "MVN_HOME=%~1"

set "MVN_CMD=%MVN_HOME%\bin\mvn.bat"
if not exist "%MVN_CMD%" (
    echo [ERROR] Maven not found: %MVN_CMD%
    echo Usage: install-3rd-party-jars.bat [mavenHome]
    exit /b 1
)

set "LIB_DIR=%~dp0lib"

echo [1/3] install ojdbc6
call "%MVN_CMD%" -q install:install-file -Dfile="%LIB_DIR%\ojdbc6-11.2.0.4.jar" -DgroupId=com.oracle.database.jdbc -DartifactId=ojdbc6 -Dversion=11.2.0.4 -Dpackaging=jar
if errorlevel 1 exit /b 1

echo [2/3] install AutoItX4Java
call "%MVN_CMD%" -q install:install-file -Dfile="%LIB_DIR%\AutoItX4Java-4.0.0.0.jar" -DgroupId=AutoItX4Java -DartifactId=AutoItX4Java -Dversion=4.0.0.0 -Dpackaging=jar
if errorlevel 1 exit /b 1

echo [3/3] install surefire-junit4 provider (needed by maven-surefire-plugin 3.0.0-M7)
call "%MVN_CMD%" -q install:install-file -Dfile="%LIB_DIR%\surefire-junit4-3.0.0-M7.jar" -DgroupId=org.apache.maven.surefire -DartifactId=surefire-junit4 -Dversion=3.0.0-M7 -Dpackaging=jar
if errorlevel 1 exit /b 1

echo Done: third-party artifacts installed into the local repository
endlocal
