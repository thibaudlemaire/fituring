
package wavFile;


public class MainTestPuls {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int BPM=120;
		
		WaveReaderAndPlayer waveRP = new WaveReaderAndPlayer();
		int count =0;
		System.out.println("Dèbut de la boucle de pulsation");
		while (count<BPM/3){ // the loop lasts 20 seconds
			
			// chemin ‡ modifer bien sur selon l'utilisateur et la machine
			waveRP.PlayWave("adresse du ficher wav à completer");
			try {
                Thread.sleep(60/BPM);// afin de respecter le BPM
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
			count++;
		}
		System.out.println("Fin de la boucle de pulsation");
	}

}