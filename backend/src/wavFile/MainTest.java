package wavFile;

public class MainTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		WaveReaderAndPlayer waveRP = new WaveReaderAndPlayer();
		WaveWriter waveWriter = new WaveWriter();
		
		/* Initialisation des fréquences et amplitudes nécessaires à l'écriture d'un fichier son*/
		int[] freq_table ={50, 200, 2000, 10000};
		double[] range_table = {3, 2 , 2, 3};
		
		/*Enregistrement du fichier créé dans un dossier personel, chemin à modifier selon utilisateur */
		waveWriter.WriteWave("C:/Users/NotAfraid/documents/Sons-PACT/test2.wav", 44000, 5, freq_table, range_table);
		waveRP.ReadWave("C:/Users/NotAfraid/documents/Sons-PACT/test2.wav");
		waveRP.PlayWave("C:/Users/NotAfraid/documents/Sons-PACT/test2.wav");
	}

}
