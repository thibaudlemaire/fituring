package serveur;

public class Test {

	public static void main(String[] args) {
		Serveur serveur = new Serveur();
		serveur.run();
		System.out.println(serveur.getVolume());
	}

}
