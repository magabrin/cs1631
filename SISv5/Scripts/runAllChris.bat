@echo off
start cmd /c runserver.bat
start cmd /c runInitializer.bat
pause
cd  runIndividualComp
start cmd /c runVotingComponent.bat
start cmd /c runGUI.bat