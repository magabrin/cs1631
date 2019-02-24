@echo off
title VotingComponent

javac -sourcepath ../../Component/VotingComponent -cp ../../Components/* ../../Components/VotingComponent/*.java
start "VotingComponent" /D"../../Components/VotingComponent" java -cp .;../* CreateVotingComponent