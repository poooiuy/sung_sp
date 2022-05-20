@echo off&setlocal
set base=%~dp0
set libdir=%~dp0..\lib
set classpath=%base%bin;%libdir%\*
echo java -classpath %classpath% com.lgcns.test.RunManager
java -classpath %classpath% com.lgcns.test.RunManager
