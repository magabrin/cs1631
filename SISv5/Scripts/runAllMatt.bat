@echo off
start cmd /c runserver.bat
pause
start cmd /c runInitializer.bat
pause
cd  runIndividualComp
start cmd /c runVotingComponent.bat
pause
start cmd /c runGUI.bat
pause
start cmd /c runTest.bat