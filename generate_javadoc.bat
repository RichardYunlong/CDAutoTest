@echo off
chcp 65001 > nul
cls

echo ===============================================
echo    CDAutoTest Project Javadoc Generator
echo ===============================================

setlocal EnableDelayedExpansion

REM ---------------------------------------------------------------- 菜单
:menu
echo.
echo Select an option:
echo 1. Generate Javadoc
echo 2. Copy to API_DOC and Open
echo 3. One-Click Generate and Open
echo 4. Exit
echo.
set /p choice=Enter your choice (1-4): 

if "%choice%"=="1" goto generate
if "%choice%"=="2" goto copy_and_open
if "%choice%"=="3" goto generate
if "%choice%"=="4" goto :eof

echo Invalid input, please try again!
goto menu

REM ---------------------------------------------------------------- 生成
:generate
echo.
echo Starting Javadoc Generation...
echo.

REM Create the target directory if it doesn't exist
if not exist ".\target\site\apidocs" (
  mkdir ".\target\site\apidocs" 2>nul
)

call mvn javadoc:javadoc -Dcharset=UTF-8 -Dencoding=UTF-8

if %errorlevel% neq 0 (
    echo Javadoc generation failed!
    pause
    goto :eof
)

echo.
echo Javadoc generated successfully! Location: .\target\site\apidocs

REM ------------------------------------------------- 询问是否复制到 API_DOC
:confirm_copy
echo.
set /p copy_choice=Continue to copy to release/API_DOC and open in browser? (y/n): 
if /i "%copy_choice%"=="y" goto copy_and_open
if /i "%copy_choice%"=="yes" goto copy_and_open
echo Operation completed, skipping copy and open.
pause
goto :eof

REM ---------------------------------------------------------------- 复制并打开
:copy_and_open
echo.
echo Copying Javadoc to release/API_DOC directory...
echo.

REM 先清空旧文档，避免历史残留文件混入新文档
if exist ".\release\API_DOC" (
  rd /s /q ".\release\API_DOC" 2>nul
)
if not exist ".\release\API_DOC" (
  mkdir ".\release\API_DOC" 2>nul
)

xcopy /E /I /Y ".\target\site\apidocs" ".\release\API_DOC\" > nul

if !errorlevel! equ 0 (
    echo Copy successful! Location: .\release\API_DOC
) else (
    echo Copy failed!
    pause
    goto :eof
)

echo Opening API Documentation...
start "" ".\release\API_DOC\index.html"

echo Operation completed!
pause
goto :eof
