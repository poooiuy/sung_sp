@echo off&setlocal
set base=%~dp0
set classpath=%base%bin;lib\*
cd %base%

java -classpath %classpath% com.lgcns.test.RunManager %1 %2 %3 %4 %5 %6 %7 %8 %9
