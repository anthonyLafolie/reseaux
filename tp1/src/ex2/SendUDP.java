package ex2;

import java.net.*;
import java.io.*;

public class SendUDP {
	public static void usage() {
		System.out.println("usage : sendUDP message");
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			DatagramPacket p;
			MulticastSocket s;
			InetAddress group;
			int port = 7654;
			try {
				group = InetAddress.getByName("224.0.0.1");
				s = new MulticastSocket(7654);
				s.joinGroup(group);

				byte[] bytes = args[0].getBytes();
				p = new DatagramPacket(bytes, args[0].length(), group, port);
				s.send(p);
				s.leaveGroup(group);
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			usage();

	}
}
