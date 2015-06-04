import java.io.*;
import java.net.URL;
import java.util.*;
import javax.sound.sampled.*;

public class SampledPlay implements PlayInterface {
   AudioWrapper aw;

   public void init(Object parent, String fileName) throws Exception {
      aw = loadAudio(fileName);
   }

   public void dispose() { 
      aw = null;
   }


   public void play() throws Exception {
      aw.play();
   }

   public void play(Object parent, String fileName) throws Exception {
      aw = loadAudio(fileName);
      aw.play();
   }

   public void stop() { 
     try { aw.stop(); } catch (Exception ex) { }
   }

   private AudioWrapper loadAudio(String fileName) throws Exception {
      // We read all bytes to ByteInputStream first then use it 
      // as a source stream. We probably could stream directly
      // from files but then must use FIS opened for longer time.
      FileInputStream fis = new FileInputStream(fileName);
      InputStream is = loadStream(fis);
      AudioInputStream ais = AudioSystem.getAudioInputStream(is);
      fis.close();

      aw = new AudioWrapper();
      aw.af   = ais.getFormat();
      int size = (int)(aw.af.getFrameSize() * ais.getFrameLength());
      aw.info = new DataLine.Info(Clip.class, aw.af, size);
      aw.data = new byte[size];
      ais.read(aw.data, 0, aw.data.length);
      return aw;
   }

   // load inputstream to ByteArrayInputStream
   private ByteArrayInputStream loadStream(InputStream is)
         throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte data[] = new byte[1024];
      for(int i = is.read(data); i != -1; i = is.read(data))
         baos.write(data, 0, i);
      baos.close();
      return new ByteArrayInputStream(baos.toByteArray());
  }

   private class AudioWrapper {
      public AudioFormat af;
      public int size;
      public DataLine.Info info;
      public byte[] data;
      // int size = data.length;
      private Clip clip;

      public void play() throws UnsupportedAudioFileException, LineUnavailableException {
         if (clip != null)
            stop();
         clip = (Clip)AudioSystem.getLine(info);
         clip.open(af, data, 0, data.length);
         clip.start();

         // clip.start();
         // clip.loop(Clip.LOOP_CONTINUOUSLY);
      }

      public void stop() throws UnsupportedAudioFileException, LineUnavailableException {
         if (clip != null)
           clip.stop();
         clip = null;         
      }
   }

}
