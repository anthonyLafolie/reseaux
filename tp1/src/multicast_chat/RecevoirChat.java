package multicast_chat;

import java.net.*;
import java.io.*;

class RecevoirChat extends Thread {
  private Chat chat;

  RecevoirChat(Chat chat) {
    this.chat = chat;
  }

  public void run() {
    try {
      // Création de la socket multicast utilisée pour recevoir les données
      MulticastSocket s  = new MulticastSocket(chat.getPort());
      // On rejoins le groupe multicast
      s.joinGroup(chat.getAdresse());
      String message = null;
      byte[] tampon;
      DatagramPacket paquet;
      // Boucler tant que la discussion n'est pas finie
      while(!chat.estFini()) {
          // Création du tampon et du paquet qui va recevoir les données
          tampon = new byte[1000];
          paquet = new DatagramPacket(tampon, tampon.length);
          // On se bloque en attente de données
          s.receive(paquet);
          // Traduction en chaîne de caractères des données reçues et affichage
          message = new String(tampon, 0, paquet.getLength());
          System.out.println(message);
        }
		//s.leaveGroup(chat.getAdresse());
		s.close();
    }
    catch(IOException exc) {
      exc.printStackTrace();
    }
  }
}