@echo off
chcp 65001 > nul
setlocal

REM 获取当前脚本所在的目录
set "SCRIPT_DIR=%~dp0"

REM 设置源目录和目标目录
set "SOURCE_DIR=%SCRIPT_DIR%target\site\apidocs"
set "TARGET_DIR=%SCRIPT_DIR%API_DOC"

echo.
echo ========================================
echo     Javadoc Management Tool
echo ========================================
echo.

:menu
echo Please select an option:
echo 1. Generate and copy Javadoc to API_DOC directory
echo 2. Open local Javadoc documentation
echo 3. Regenerate Javadoc and overwrite API_DOC
echo 4. Update existing API_DOC (copy new content only)
echo 5. Exit
echo.
set /p choice=Enter your choice (1-5): 

if "%choice%"=="1" goto copy_docs
if "%choice%"=="2" goto open_docs
if "%choice%"=="3" goto regenerate_docs
if "%choice%"=="4" goto update_docs
if "%choice%"=="5" goto exit_script

echo Invalid option, please try again.
goto menu

:copy_docs
if not exist "%SOURCE_DIR%" (
    echo Source directory does not exist, generating Javadoc...
    mvn javadoc:javadoc
    if %errorlevel% neq 0 (
        echo Javadoc generation failed, please check errors.
        pause
        exit /b 1
    )
)

if not exist "%TARGET_DIR%" (
    echo Creating API_DOC directory...
    mkdir "%TARGET_DIR%"
)

echo Copying Javadoc documentation to API_DOC directory...
xcopy /E /I /Y "%SOURCE_DIR%" "%TARGET_DIR%"
if %errorlevel% equ 0 (
    echo Successfully copied Javadoc documentation to %TARGET_DIR%
) else (
    echo Error occurred during copying
)
pause
goto menu

:open_docs
if exist "%TARGET_DIR%\index.html" (
    echo Opening Javadoc documentation...
    start "" "%TARGET_DIR%\index.html"
) else (
    echo Error: Javadoc documentation not found. Please execute option 1 first to generate documentation.
)
pause
goto menu

:update_docs
if not exist "%SOURCE_DIR%" (
    echo Source directory does not exist, generating Javadoc...
    mvn javadoc:javadoc
    if %errorlevel% neq 0 (
        echo Javadoc generation failed, please check errors.
        pause
        goto menu
    )
)

if not exist "%TARGET_DIR%" (
    echo API_DOC directory does not exist, please execute option 1 first.
    pause
    goto menu
)

echo Updating API_DOC directory...
xcopy /E /I /Y "%SOURCE_DIR%" "%TARGET_DIR%"
if %errorlevel% equ 0 (
    echo Successfully updated Javadoc documentation in %TARGET_DIR%
) else (
    echo Error occurred during update
)
pause
goto menu

:regenerate_docs
echo Regenerating Javadoc...
mvn javadoc:javadoc
if %errorlevel% neq 0 (
    echo Javadoc generation failed, please check errors.
    pause
    exit /b 1
)

if exist "%TARGET_DIR%" (
    echo Deleting old API_DOC directory...
    rmdir /s /q "%TARGET_DIR%"
)

echo Creating API_DOC directory...
mkdir "%TARGET_DIR%"

echo Copying newly generated Javadoc documentation...
xcopy /E /I /Y "%SOURCE_DIR%" "%TARGET_DIR%"
if %errorlevel% equ 0 (
    echo Successfully copied new Javadoc documentation to %TARGET_DIR%
) else (
    echo Error occurred during copying
)
pause
goto menu

:exit_script
echo Exiting script.
endlocal