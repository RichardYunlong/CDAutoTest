@echo off
cls

setlocal EnableDelayedExpansion

set lib=.\lib
set classpath=.

FOR /R %lib% %%f IN (*.jar) DO (
    set classpath=!classpath!;"%%f"
)

"%JAVA_HOME%\bin\java" -server -Xms1024M -Xmx1024M -classpath %classpath% main.java.AutoTest.GRunTest
pause