package syntheseAudio;

import javax.sound.sampled.*;
import java.io.*;
 
public class JavaSoundRecorder {
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
    // Define the audio format
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    // Method to begin capturing and recording the sound, creating a file at the given path
    void start(String path) {
    	File soundFile = new File (path);
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            
         // start capturing
            line.start();   
 
            System.out.println("Début de la capture");
 
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Début de l'enregistrement");
 
            // start recording
            AudioSystem.write(ais, fileType, soundFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    // Method to end capturing/recording 
    void finish() {
        line.stop();
        line.close();
        System.out.println("Fin de la capture");
    }
}