@echo off&setlocal
set base=%~dp0
set libdir=%~dp0..\lib
set classpath=%base%..\..\target\classes\sung05_exam2022_httpProxyServer_sub3;%libdir%\*

start java -classpath %classpath% com.lgcns.test.RunManager %base%Proxy-1.json
start java -classpath %classpath% com.lgcns.test.RunManager %base%Proxy-2.json
