package ex1;

import java.net.*;

import java.io.*;

public class ReceiveUDP {

	public static void usage() {
		System.out.println("usage : ReceiveUDP port, port is an integer");
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			DatagramSocket s;
			DatagramPacket p;
			try {
				int port = Integer.parseInt(args[0]);
				s = new DatagramSocket(port);
				p = new DatagramPacket(new byte[512], 512);
				s.receive(p);
				System.out.println(
						"paquet reçu de : " + p.getAddress() + " port " + p.getPort() + " taille " + p.getLength());
				byte array[] = p.getData();
				for (int i = 0; i < p.getLength(); i++)
					System.out.println("array[" + i + "] = " + array[i]);
				String message = new String(array);
				System.out.println("message : " + message);
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			usage();
	}

}
