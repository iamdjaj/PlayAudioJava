import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import net.java.games.joal.AL;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;
import net.java.games.joal.OpenALException;

/**
 * https://joal-demos.dev.java.net/
 * http://developer.creative.com/landing.asp?cat=1&sbcat=31
 * JOAL is cross-platform SoundAPI with 3DSound capabilities.
 * So its a relative complex just to play a simple wav if
 * you compare sourcecode to AppletAudioPlay. But it
 * gives a lot more features creating a complete
 * sound environment in games project.
 */
public class JOALAudioPlay implements PlayInterface {
    static AL al;

    // Buffers hold sound data.
    static int[] buffer = new int[1];

    // Sources are points emitting sound.
    static int[] source = new int[1];

    // Position of the source sound.
    static float[] sourcePos = { 0.0f, 0.0f, 0.0f };

    // Velocity of the source sound.
    static float[] sourceVel = { 0.0f, 0.0f, 0.0f };

    // Position of the listener.
    static float[] listenerPos = { 0.0f, 0.0f, 0.0f };

    // Velocity of the listener.
    static float[] listenerVel = { 0.0f, 0.0f, 0.0f };

    // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
    static float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

    public void init(Object parent, String fileName) throws Exception { 
       initJOAL();
       loadAudioData(fileName);
    }

    public void play() throws Exception { 
       long start = System.currentTimeMillis();

       int[] state = new int[1];
       al.alSourcePlay(source[0]);
       while(true) {
         al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state);
         if (state[0] != AL.AL_PLAYING)
            break;
         Thread.currentThread().sleep(1L);
       }

       long end2 = System.currentTimeMillis();
       System.out.println("Duration: " + (end2 - start));
    }

    public void play(Object parent, String fileName) throws Exception {
       long start = System.currentTimeMillis();

       initJOAL();
       loadAudioData(fileName);
       //setListenerValues();

       int[] state = new int[1];

       al.alSourcePlay(source[0]);

       while(true) {
         al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state);
         if (state[0] != AL.AL_PLAYING)
            break;
         Thread.currentThread().sleep(1L);
       }

       long end2 = System.currentTimeMillis();
       System.out.println("Duration: " + (end2 - start));
    }

    public void stop() {
       al.alSourceStop(source[0]);
    }
    
    public void dispose() {
       if (al == null) return;
       al.alDeleteBuffers(1, buffer);
       al.alDeleteSources(1, source);
       ALut.alutExit();
    }

    private static void initJOAL() throws Exception {
        if (al != null) return;

        // Initialize OpenAL and clear the error bit
        try {
	    al = ALFactory.getAL();
            ALut.alutInit();
            al.alGetError();
        } catch (OpenALException ex) {
            throw ex;
        }
    }

    private static void loadAudioData(String fileName) throws Exception {
        // variables to load into
        int[] format = new int[1];
        ByteBuffer[] data = new ByteBuffer[1];
        int[] size = new int[1];
        int[] freq = new int[1];
        int[] loop = new int[1];

        // Load wav data into a buffer and then release data we dont need anymore
        al.alGenBuffers(1, buffer);
        if (al.alGetError() != AL.AL_NO_ERROR)
           throw new OpenALException("OpenAL alGenBuffers failed (" + al.alGetError() + ")");

        ALut.alutLoadWAVFile(fileName,
            format,
            data,
            size,
            freq,
            loop);
        al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);
        ALut.alutUnloadWAV(format[0], data[0], size[0], freq[0]);

        // Bind buffer with a source
        al.alGenSources(1, source);
        if (al.alGetError() != AL.AL_NO_ERROR)
           throw new OpenALException("OpenAL alGenSources failed (" + al.alGetError() + ")");

        // define source properties to be used while its in playback
        al.alSourcei(source[0], AL.AL_BUFFER, buffer[0]);  // use this audiosample
        al.alSourcef(source[0], AL.AL_PITCH, 1.0f);
        al.alSourcef(source[0], AL.AL_GAIN, 1.0f);
        al.alSourcefv(source[0], AL.AL_POSITION, sourcePos);
        al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel);
        al.alSourcei(source[0], AL.AL_LOOPING, loop[0]);

        // Do another error check and return.
        if (al.alGetError() != AL.AL_NO_ERROR)
           throw new OpenALException("OpenAL alSource failed (" + al.alGetError() + ")");
    }

    private static void setListenerValues() {
        // set position of "human listener" in 3D space
        al.alListenerfv(AL.AL_POSITION, listenerPos);
        al.alListenerfv(AL.AL_VELOCITY, listenerVel);
        al.alListenerfv(AL.AL_ORIENTATION, listenerOri);
    }

}
