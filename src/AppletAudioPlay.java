import java.applet.*;
import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Play audioclip using Applet AudioClip implementation.
 **/
public class AppletAudioPlay implements PlayInterface {
  private AudioClip currentClip;

  public void init(Object parent, String fileName) throws Exception {
     URL url = new URL("file:" + fileName);
     AudioClip clip = Applet.newAudioClip(url);
     currentClip = clip;
  }

  public void play() throws Exception {
     currentClip.play();
  }

  public void play(Object parent, String fileName) throws Exception {
     URL url = new URL("file:" + fileName);
     AudioClip clip = Applet.newAudioClip(url);
     currentClip = clip;
     clip.play();
  }   

  public void stop() {
     if (currentClip != null)
       currentClip.stop();
  }

  public void dispose() { 
    currentClip = null;
  }

}
