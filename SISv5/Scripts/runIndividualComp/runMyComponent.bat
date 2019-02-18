@echo off
title MyComponent

javac -sourcepath ../../Component/MyComponent -cp ../../Components/* ../../Components/MyComponent/*.java
start "MyComponent" /D"../../Components/MyComponent" java -cp .;../* CreateMyComponent