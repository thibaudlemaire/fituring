package syntheseAudio;

import javax.sound.sampled.SourceDataLine;

import wavFile.WaveReaderAndPlayer;

public class AudioThread extends Thread{
	
	String filePath;
	//SourceDataLine sourceLine;
	
	public AudioThread(String filePath/*, SourceDataLine sourceLine*/){
		super();
		this.filePath=filePath;
		//this.sourceLine=sourceLine;
	}
	
	public void run() {
		WaveReaderAndPlayer waveRP =new WaveReaderAndPlayer() ;
		while(true){
		try {
			waveRP.PlayWave(filePath/*, sourceLine*/);
			System.out.println("audio");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

}
