package syntheseAudio;

import syntheseAudio.JavaSoundRecorder;

public class MainCapture {
	
	public static void main(String[] args) {
        final JavaSoundRecorder recorder = new JavaSoundRecorder();
        
        // temps d'enregistrement (en millisecondes)
        long recordTime = 20000;
 
        // crée un nouveau thread qui va permmetre d'enregistrer pendant recordTime, puis qui arrête la fonction
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
 
        // début de la capture et de l'enregistrement
        // L'endroit où le son enregistré doit être stocké est bien évidemment personnalisable et dépend des utilisateurs
        recorder.start("C:/Users/NotAfraid/testCapture.wav");
    }
}


