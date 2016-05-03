@echo off

rem This Batch file is for updating Minecraft patch File

rem branches:
rem Master - Vinilla MC
rem Opiates - Modified Opiates MC
rem Temp - temp branch

set mc-dir="C:\Users\Hexeption\Desktop\Hacked Clients\Workspace\Opiates Minecraft 1.9.X"
set opiates-dir="C:\Users\Hexeption\Desktop\Hacked Clients\Opiates"

cd %mc-dir%
git checkout master
git checkout -b temp
git merge --squash Opiates
git commit -a -m "Opiates"
git format-patch master --ignore-space-change
git checkout Opiates
git branch -D temp
move /y 0001-Opiates.patch %opiates-dir%\Minecraft Patch\minecraft.patch
rem pause