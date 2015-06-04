import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

// http://www.jsresources.org/examples/SimpleAudioPlayer.java.html
public class AudioAPIPlay implements PlayInterface {
   private static final int	EXTERNAL_BUFFER_SIZE = 4 * 1024; //128000;
   private AudioFormat audioFormat;
   private SourceDataLine line;
   private byte[] audioBytes;

   public void init(Object parent, String fileName) throws Exception {
      // load soundfile from file and cache audiobytes
      File soundFile = new File(fileName);
      AudioInputStream audioInputStream = null;
      try {
          audioInputStream = AudioSystem.getAudioInputStream(soundFile);
      } catch (Exception ex) {
          throw ex;
      }

      // audioinformat of soundfile and audioline
      audioFormat = audioInputStream.getFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      try {
         line = (SourceDataLine)AudioSystem.getLine(info);
      } catch (LineUnavailableException ex) { 
         throw ex;
      }

      // write data to buffer
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int nBytesRead = 0;
      byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
      while (nBytesRead != -1) {
         try {
             nBytesRead = audioInputStream.read(abData, 0, abData.length);
         } catch (IOException ex) {
             throw ex;
         }
         if (nBytesRead > 0) {
             baos.write(abData, 0, nBytesRead);
         }
      }
      audioBytes = baos.toByteArray();
   }

   public void play() throws Exception {
      long start = System.currentTimeMillis();
      // open audiodata line
      line.open(audioFormat);
      line.start();
      line.write(audioBytes, 0, audioBytes.length);
      line.drain();
      long end = System.currentTimeMillis();
      line.close();
      System.out.println("duration: " + (end - start));
   }

   public void play(Object parent, String fileName) throws Exception {
      long start = System.currentTimeMillis();

      // load soundfile from file
      File soundFile = new File(fileName);
      AudioInputStream audioInputStream = null;
      try {
          audioInputStream = AudioSystem.getAudioInputStream(soundFile);
      } catch (Exception ex) {
          throw ex;
      }

      // audioinformat of soundfile and audioline
      AudioFormat audioFormat = audioInputStream.getFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      SourceDataLine line;

      try {
         // open audiodata line
         line = (SourceDataLine)AudioSystem.getLine(info);
         line.open(audioFormat);
      } catch (LineUnavailableException ex) { 
         throw ex;
      }

      // line can now receive data
      line.start();

      long start1 = System.currentTimeMillis();
      // write data to line
      int nBytesRead = 0;
      byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
      while (nBytesRead != -1) {
         try {
             nBytesRead = audioInputStream.read(abData, 0, abData.length);
         } catch (IOException ex) {
             throw ex;
         }
         if (nBytesRead > 0) {
             int nBytesWritten = line.write(abData, 0, nBytesRead);
         }
      }

      // wait until all data is played
      line.drain();

      // close line
      line.close();

      long end = System.currentTimeMillis();
      System.out.println("duration: " + (end - start));
      System.out.println("duration1: " + (end - start1));
   }


   public void stop() {
      line.close();
   }

   public void dispose() {
      if (line != null)
         line.close();
   }

}

