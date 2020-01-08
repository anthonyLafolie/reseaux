package multicast_chat;

import java.net.*;
import java.io.*;

public class Chat {
	private boolean fini = false;
	private int port = 12345;
	private InetAddress adr = null;

	synchronized void finir() {
		fini = true;
	}

	synchronized boolean estFini() {
		return fini;
	}

	void setPort(int p) {
		port = p;
	}

	int getPort() {
		return port;
	}

	void setAdresse(InetAddress a) {
		adr = a;
	}

	InetAddress getAdresse() {
		return adr;
	}

	public static void main(String argv[]) throws Exception {
		Chat chat = new Chat();

		InetAddress adresse = InetAddress.getByName("224.0.0.1");
		chat.setAdresse(adresse);
		int port = 7654;
		chat.setPort(port);

		// Démarre le thread de réception
		RecevoirChat r = new RecevoirChat(chat);
		r.start();

		// Création de la socket multicast utilisée pour envoyer les données
		MulticastSocket socketEnvoi = new MulticastSocket();
		DatagramPacket paquet;
		byte[] tampon;
		String message;
		String message_complet;
		String nom;
		
		// Création du BufferedReader connecté sur l'entrée standard
		BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
		
		// demande du nom est affichage de la connexion
		System.out.println("Tapez votre nom :");
		nom = entree.readLine();
		message_complet = nom + " vient de se connecter !";
		// Création du datagramme à envoyer
		tampon = message_complet.getBytes();
		paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
		// Envoi du datagramme
		socketEnvoi.send(paquet);
		System.out.println("Tapez vos phrases en terminant par FIN :");
		
		// envoyer des messages
		do {
			// Attente d'une entrée de l'utilisateur
			message = entree.readLine();
			if (!message.equals("FIN")) {
				message_complet = nom + " à dit : " + message;
				// Création du datagramme à envoyer
				tampon = message_complet.getBytes();
				paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
				// Envoi du datagramme
				socketEnvoi.send(paquet);
			}
		}
		// Boucler tant qu'on n'a pas reçu le mot de la fin
		while (!message.equals("FIN"));
		// modifier le booléen
		chat.finir();
		// affichage de la deconnexion du chat 
		message_complet = nom + " quitte le chat.";
		// Création du datagramme à envoyer
		tampon = message_complet.getBytes();
		paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
		// Envoi du datagramme
		socketEnvoi.send(paquet);
	}
}