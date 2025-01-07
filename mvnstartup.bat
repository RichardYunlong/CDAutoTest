@echo off
set allureroot = %~f0
echo pwd：%allureroot%
cd %allureroot%
echo please wait...
for /l %%a in (3,-1,0) do (
 echo %%a
 for /l %%b in (1,1,7000) do (ver >nul)
 cls
)
echo run test
call ./mvnJUnit.bat >./mvnJUnit.log
echo generate report
call ./mvnAllure.bat >./mvnAllure.log
echo show report
allure open allure-report
pause