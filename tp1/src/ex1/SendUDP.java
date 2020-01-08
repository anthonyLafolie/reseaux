package ex1;

import java.net.*;
import java.io.*;

public class SendUDP {
	public static void usage() {
		System.out.println("usage : sendUDP nameOfTheComputer port message, port is an integer");
	}

	public static void main(String[] args) {
		if (args.length == 3) {
			DatagramPacket p;
			DatagramSocket s;
			InetAddress dst;
			try {
				dst = InetAddress.getByName(args[0]);
				int port = Integer.parseInt(args[1]);
				byte[] bytes = args[2].getBytes();
				p = new DatagramPacket(bytes, args[2].length(), dst, port);
				s = new DatagramSocket();
				s.send(p);
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				usage();
			}
		} else
			usage();

	}
}
