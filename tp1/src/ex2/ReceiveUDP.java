package ex2;

import java.net.*;

import java.io.*;

public class ReceiveUDP {

	public static void usage() {
		System.out.println("usage : ReceiveUDP ");
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			MulticastSocket s;
			DatagramPacket p;
			try {
				InetAddress group = InetAddress.getByName("224.0.0.1");
				s = new MulticastSocket(7654);
				s.joinGroup(group);

				p = new DatagramPacket(new byte[512], 512);
				s.receive(p);
				System.out.println(
						"paquet reçu de : " + p.getAddress() + " port " + p.getPort() + " taille " + p.getLength());
				byte array[] = p.getData();
				for (int i = 0; i < p.getLength(); i++)
					System.out.println("array[" + i + "] = " + array[i]);
				String message = new String(array);
				System.out.println("message : " + message);
				s.leaveGroup(group);
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			usage();
	}

}
