package serveur;

public class Test {

	public static void main(String[] args) {
		Serveur serveur = new Serveur();
		//serveur.run();
		Thread th = new Thread(serveur);
		th.start();
		int volume = 0;
		int style = 0;
		try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
