package mta.se.proiectchat.mainpackage.audiopackage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * Created by ADY on 24.01.2015.
 */
public class Playback {

    private AudioFormat audioFormatUsed;
    private SourceDataLine playbackLineUsed;

    public static int dataSize;

    public Playback(){
        dataSize=1500;
        audioFormatUsed = new AudioFormat(8000.0f,16,1,true,true);
        playbackLineUsed=null;
    }

    public void play(byte[] capturedData){
        playbackLineUsed.write(capturedData,0, capturedData.length);
    }

    public void openLineForPlayback(){
        try{
            DataLine.Info dlInfo = new DataLine.Info(SourceDataLine.class,audioFormatUsed);

            playbackLineUsed = (SourceDataLine) AudioSystem.getLine(dlInfo);
            playbackLineUsed.open(audioFormatUsed);
            playbackLineUsed.start();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeLineForPlayback(){
        playbackLineUsed.drain();
        playbackLineUsed.close();
    }

}
