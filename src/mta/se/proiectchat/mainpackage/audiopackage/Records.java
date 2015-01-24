package mta.se.proiectchat.mainpackage.audiopackage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * Created by ADY on 24.01.2015.
 */
public class Records {


    private AudioFormat audioFormatUsed;
    private TargetDataLine pickupLineUsed;

    private byte[] capturedData;

    public static int dataSize;

    public Records(){
        dataSize=1500;
        capturedData=null;
        audioFormatUsed=null;
        pickupLineUsed=null;
    }

    public byte[] captureAudioData(){

        int check = pickupLineUsed.read(capturedData,0,dataSize);
        return capturedData;
    }

    public void openPickupLine(){
        try {
            pickupLineUsed = AudioSystem.getTargetDataLine(audioFormatUsed);
            DataLine.Info dlInfo = new DataLine.Info(TargetDataLine.class,audioFormatUsed);

            pickupLineUsed = (TargetDataLine) AudioSystem.getLine(dlInfo);
            pickupLineUsed.open(audioFormatUsed);
            capturedData = new byte[pickupLineUsed.getBufferSize() /5];
            pickupLineUsed.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closePickupLine(){
        pickupLineUsed.close();
    }

}
