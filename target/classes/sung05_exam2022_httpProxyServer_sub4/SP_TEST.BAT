@echo off&setlocal
set base=%~dp0
set libdir=%~dp0..\..\lib
set classpath=%base%..\..\target\classes\sung05_exam2022_httpProxyServer_sub4;%libdir%\*

echo %base%
start java -classpath %classpath%RunManager %base%Proxy-1.json
start java -classpath %classpath%RunManager %base%Proxy-2.json
start java -classpath %classpath%RunManager %base%Proxy-3.json
