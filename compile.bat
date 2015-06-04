call setenv.bat

set cp=./lib/joal.jar
javac.exe -classpath %cp% -sourcepath ./src -d ./classes ./src/*.java

xcopy /Y .\src\META-INF\*.* .\classes\META-INF\

pause