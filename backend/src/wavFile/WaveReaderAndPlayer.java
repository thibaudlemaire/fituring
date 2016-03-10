package wavFile;

import java.io.*;
import javax.sound.sampled.*;

public class WaveReaderAndPlayer
{
	    
	public void ReadWave (String fileName) throws Exception
	{
		try
		{
			
			File file=new File(fileName);
			
			// Open the wave file specified as the first argument
			WavFile wavFile = WavFile.openWavFile(file);

			// Display information about the wave file
			wavFile.display();

			// Get the number of audio channels in the wave file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;

			do
			{
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);

				// Loop through frames and look for minimum and maximum value
				for (int s=0 ; s<framesRead * numChannels ; s++)
				{
					if (buffer[s] > max) max = buffer[s];
					if (buffer[s] < min) min = buffer[s];
				}
				
				
			}
			while (framesRead != 0);

			// Close the wavFile
			wavFile.close();

			// Output the minimum and maximum value
			System.out.printf("Min: %f, Max: %f\n", min, max);
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
	
	public void PlayWave (String fileName) throws Exception {
		
		AudioInputStream audioStream = null;
		AudioFormat audioFormat;
		
		try{
			 audioStream = AudioSystem.getAudioInputStream(new File(fileName));
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		audioFormat = audioStream.getFormat();
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceLine = null;
		try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[12800]; // 12800 : buffer size
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
	}
}

