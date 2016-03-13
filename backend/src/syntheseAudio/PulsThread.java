package syntheseAudio;

import javax.sound.sampled.SourceDataLine;

import wavFile.WaveReaderAndPlayer;

public class PulsThread extends Thread{
	
	int BPM;
	String filePath;
	//SourceDataLine sourceLine;
	
	public PulsThread(int BPM, String filePath /*SourceDataLine sourceLine*/){
		super();
		this.BPM=BPM;
		this.filePath= filePath;
		//this.sourceLine = sourceLine;
	}
	
	public void run(){
		WaveReaderAndPlayer waveRP = new WaveReaderAndPlayer();
		while (true) {
			try {
				waveRP.PlayWave(filePath /*sourceLine*/);
				System.out.println("pulse");
				PulsThread.sleep(60/BPM);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
