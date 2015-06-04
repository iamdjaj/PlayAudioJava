Test Java Audio
==================
http://koti.mbnet.fi/akini/

Test various audio libraries. Application
displayes a JFrame form.
- select an audio file to be played
- click [ play ]
- button [ initCache ] preloads a file and plays it from ram,
  this should minimize IO overhead on some implementations.
  Not all APIs need this.


NOTES
======
- not all implementations use background thread for
  play. So play button might block UI. This is just
  due to a lazy programming and not problem of the
  appropriate audio library.
- not all implementations use cached play button.
- not all implementations play .mp3, .mid files.
- some implementations use same library but 
  using different technique, so not all support
  same audio formats.
- this is very quick and dirty hack for simple
  java audio tests, so bear with me.


HOW TO RUN
============
Command arguments:
java.exe .... PlayWAV <playInterface>

playInterface: 
name of implementation class to play an audio file
   SampledPlay     = javax.sound.sampled.*
   JOALAudioPlay   = net.java.games.joal.AL
   AudioAPIPlay    = javax.sound.sampled.*
   MP3Play         = javax.sound.sampled.* 
   AppletAudioPlay = java.applet.*


.bat scripts
==============
setenv.bat
  update "set path=" to reference your JDK folder, 
  without this other .bat files might not work.

compile.bat
  compile sources

jar.bat
  create lib/playwav.jar library

go.bat
  run PlayWAV test application
  update "set playInterface=" value for given implementation class
  see supported class names in previous topic.

Libraries
============
JOAL
  https://joal-demos.dev.java.net/
  http://developer.creative.com/landing.asp?cat=1&sbcat=31
  - download OpenALwEAX.exe (300KB) and install .dll

JLayer
  http://www.javazoom.net/mp3spi/sources.html
