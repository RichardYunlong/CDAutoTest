@echo off
setlocal

REM 获取当前脚本所在的目录
set "SCRIPT_DIR=%~dp0"

REM 设置源目录和目标目录
set "SOURCE_DIR=%SCRIPT_DIR%target\site\apidocs"
set "TARGET_DIR=%SCRIPT_DIR%API_DOC"

echo.
echo ========================================
echo     Javadoc 管理工具
echo ========================================
echo.

:menu
echo 请选择操作：
echo 1. 生成并复制 Javadoc 到 API_DOC 目录
echo 2. 访问本地 Javadoc 文档
echo 3. 重新生成 Javadoc 并覆盖 API_DOC
echo 4. 退出
echo.
set /p choice=请输入选项 (1-4): 

if "%choice%"=="1" goto copy_docs
if "%choice%"=="2" goto open_docs
if "%choice%"=="3" goto regenerate_docs
if "%choice%"=="4" goto exit_script

echo 无效选项，请重试。
goto menu

:copy_docs
if not exist "%SOURCE_DIR%" (
    echo 源目录不存在，正在生成 Javadoc...
    mvn javadoc:javadoc
    if %errorlevel% neq 0 (
        echo Javadoc 生成失败，请检查错误。
        pause
        exit /b 1
    )
)

if not exist "%TARGET_DIR%" (
    echo 创建 API_DOC 目录...
    mkdir "%TARGET_DIR%"
)

echo 正在复制 Javadoc 文档到 API_DOC 目录...
xcopy /E /I /Y "%SOURCE_DIR%" "%TARGET_DIR%"
if %errorlevel% equ 0 (
    echo 成功复制 Javadoc 文档到 %TARGET_DIR%
) else (
    echo 复制过程中出现错误
)
pause
goto menu

:open_docs
if exist "%TARGET_DIR%\index.html" (
    echo 正在打开 Javadoc 文档...
    start "" "%TARGET_DIR%\index.html"
) else (
    echo 错误：找不到 Javadoc 文档。请先执行选项 1 生成文档。
)
pause
goto menu

:regenerate_docs
echo 正在重新生成 Javadoc...
mvn javadoc:javadoc
if %errorlevel% neq 0 (
    echo Javadoc 生成失败，请检查错误。
    pause
    exit /b 1
)

if exist "%TARGET_DIR%" (
    echo 删除旧的 API_DOC 目录...
    rmdir /s /q "%TARGET_DIR%"
)

echo 创建 API_DOC 目录...
mkdir "%TARGET_DIR%"

echo 正在复制新生成的 Javadoc 文档...
xcopy /E /I /Y "%SOURCE_DIR%" "%TARGET_DIR%"
if %errorlevel% equ 0 (
    echo 成功复制新 Javadoc 文档到 %TARGET_DIR%
) else (
    echo 复制过程中出现错误
)
pause
goto menu

:exit_script
echo 退出脚本。
endlocal