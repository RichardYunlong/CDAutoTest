@echo off
cls

setlocal EnableDelayedExpansion

set lib=.\lib
set classpath=.

FOR /R %lib% %%f IN (*.jar) DO (
    set classpath=!classpath!;"%%f"
)

"%JAVA_HOME%\bin\java" -server -Dfile.encoding=UTF-8 -Xms1024M -Xmx1024M -classpath %classpath% App.cdtestapp -stop
pause