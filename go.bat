SET CP=./lib/joal.jar;./lib/playwav.jar
set cp=%cp%;./lib/jl1.0.jar;./lib/mp3spi1.9.2.jar;./lib/tritonus_share.jar


rem set playInterface=SampledPlay
rem set playInterface=JOALAudioPlay
rem set playInterface=AudioAPIPlay
rem set playInterface=AppletAudioPlay
set playInterface=MP3Play

java.exe -version
java.exe -Djava.library.path=./lib -cp %CP% PlayWAV %playInterface%

pause
