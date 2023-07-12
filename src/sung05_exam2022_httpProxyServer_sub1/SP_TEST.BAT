@echo off&setlocal
set base=%~dp0
set classpath=%base%bin
echo %classpath%

java -classpath %classpath% sung04_exam2021_sub4_byYun.RunManager %1 %2 %3 %4 %5 %6 %7 %8 %9
