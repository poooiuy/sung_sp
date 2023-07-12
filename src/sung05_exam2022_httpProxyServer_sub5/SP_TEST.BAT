@echo off&setlocal
set base=%~dp0
set libdir=%~dp0..\lib
set classpath=%base%bin;%libdir%\*

start java -classpath %classpath% com.lgcns.test.RunManager %base%Proxy-1.json
start java -classpath %classpath% com.lgcns.test.RunManager %base%Proxy-2.json
start java -classpath %classpath% com.lgcns.test.RunManager %base%Proxy-3.json
