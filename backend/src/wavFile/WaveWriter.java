package wavFile;

import java.io.*;

public class WaveWriter
{
	
	// freq_sample : fréquence d'échantillonage (en Hz)
	// duration : durée du son (en s)
	// freq_table : tableau avec les fréquences souhaitées
	// range_table : tableau avec les amplitudes souhaitées
	public void WriteWave (String fileName, int freq_sample, double duration, int[] freq_table, double[] range_table)
	{
		if (freq_table.length != range_table.length) 
			System.out.println("Il doit y avoir autant de fréquences que d'amplitude");
		
		else {
		
			int sin_nbr = freq_table.length;
		
			if (sin_nbr == 0)
				System.out.println("Il faut rentrer au moins une fréquence et une amplitude");
		
			else {
		
				try
					{
					// Calculate the number of frames required for specified duration
					long numFrames = (long)(duration * freq_sample);

					// Create a wave file with the name specified as the first argument
					WavFile wavFile = WavFile.newWavFile(new File(fileName), 2, numFrames, 16, freq_sample);

					// Create a buffer of 100 frames
					double[][] buffer = new double[sin_nbr][100];

					// Initialize a local frame counter
					long frameCounter = 0;

					// Loop until all frames written
					while (frameCounter < numFrames)
						{
						// Determine how many frames to write, up to a maximum of the buffer size
						long remaining = wavFile.getFramesRemaining();
						int toWrite = (remaining > 100) ? 100 : (int) remaining;

						// Fill the buffer, one tone per channel
						for (int s=0 ; s<toWrite ; s++, frameCounter++)
						{
							for (int i=0 ; i<sin_nbr ; i++){
								buffer[i][s] = range_table[i]*Math.sin(2.0 * Math.PI * freq_table[i] * frameCounter / freq_sample);
							}
						}

						// Write the buffer
						wavFile.writeFrames(buffer, toWrite);
						}

					// Close the wavFile
					wavFile.close();
					
					//Print the spectrum
					for (int j=0 ; j<sin_nbr ; j++){
						System.out.println("Amplitude de " + range_table[j] + " à la fréquence " + freq_table[j]);
					}
					
					}
				catch (Exception e)
				{
					System.err.println(e);
				}
			}
		}	
	}
}
