package syntheseAudio;

import syntheseAudio.JavaSoundRecorder;

public class MainCapture {
	
	public static void main(String[] args) {
        final JavaSoundRecorder recorder = new JavaSoundRecorder();
        
        // temps d'enregistrement (en millisecondes)
        long recordTime = 20000;
 
        // cr�e un nouveau thread qui va permmetre d'enregistrer pendant recordTime, puis qui arr�te la fonction
        Thread threadCapture = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(recordTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
 
        // appel du thread
        threadCapture.start();
 
        // d�but de la capture et de l'enregistrement
        // L'endroit o� le son enregistr� doit �tre stock� est bien �videmment personnalisable et d�pend des utilisateurs
        recorder.start("C:/Users/NotAfraid/testCapture.wav");
    }
}


