call setenv.bat
SET MF=./classes/META-INF/MANIFEST.MF
jar.exe cvfm ./lib/playwav.jar %MF% -C ./classes .

pause
