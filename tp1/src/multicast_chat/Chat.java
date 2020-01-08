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

		// D�marre le thread de r�ception
		RecevoirChat r = new RecevoirChat(chat);
		r.start();

		// Cr�ation de la socket multicast utilis�e pour envoyer les donn�es
		MulticastSocket socketEnvoi = new MulticastSocket();
		DatagramPacket paquet;
		byte[] tampon;
		String message;
		String message_complet;
		String nom;
		
		// Cr�ation du BufferedReader connect� sur l'entr�e standard
		BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
		
		// demande du nom est affichage de la connexion
		System.out.println("Tapez votre nom :");
		nom = entree.readLine();
		message_complet = nom + " vient de se connecter !";
		// Cr�ation du datagramme � envoyer
		tampon = message_complet.getBytes();
		paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
		// Envoi du datagramme
		socketEnvoi.send(paquet);
		System.out.println("Tapez vos phrases en terminant par FIN :");
		
		// envoyer des messages
		do {
			// Attente d'une entr�e de l'utilisateur
			message = entree.readLine();
			if (!message.equals("FIN")) {
				message_complet = nom + " � dit : " + message;
				// Cr�ation du datagramme � envoyer
				tampon = message_complet.getBytes();
				paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
				// Envoi du datagramme
				socketEnvoi.send(paquet);
			}
		}
		// Boucler tant qu'on n'a pas re�u le mot de la fin
		while (!message.equals("FIN"));
		// modifier le bool�en
		chat.finir();
		// affichage de la deconnexion du chat 
		message_complet = nom + " quitte le chat.";
		// Cr�ation du datagramme � envoyer
		tampon = message_complet.getBytes();
		paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
		// Envoi du datagramme
		socketEnvoi.send(paquet);
	}
}