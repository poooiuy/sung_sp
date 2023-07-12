@echo off&setlocal
set base=%~dp0
cd %base%
java -jar %base%..\mock\mock_sub3.jar %1 %2 %3 %4 %5 %6 %7 %8 %9
pause
