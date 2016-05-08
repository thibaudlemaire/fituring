package serveur;

public class Test {

	public static void main(String[] args) {
		Serveur serveur = new Serveur();
		//serveur.run();
		new Thread(serveur).start();
		System.out.println(serveur.getVolume());
		System.out.println(serveur.getStyle());
	}

}
