@echo off
title Test

javac -sourcepath ../../Components/Test -cp ../../Components/* ../../Components/Test/*.java
start "Test" /D"../../Components/Test" java -cp .;../* CreateTestComponent