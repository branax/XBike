package player;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client_niti {
	
	public static int inputPlayer = 0;
	DataInputStream inC = null;
	Scanner sc1 = null;
		
	Socket s = null;

	String accept;
	float input;
	KrmilnaEnota player;
	
	public Client_niti (KrmilnaEnota pp) throws IOException {
		player = pp;
		try {
			s = new Socket ("169.254.218.194", 1342);
			Scanner sc = new Scanner (System.in);
			System.out.println("");
			PrintStream p = new PrintStream (s.getOutputStream());
			p.println("y\n");
			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		while (true) {
				sc1 = new Scanner (s.getInputStream());
				String cc = sc1.nextLine();
				float faktorHitrosti = Float.parseFloat(cc);
				faktorHitrosti = (float) (faktorHitrosti * 0.1); // delimo z 10, da dobimo realne vrednosti iz procesne enote
								
				System.out.println(faktorHitrosti); //testno izpisovanje izracunane vrednosti faktorHitrosti
				player.mediaPlayerComponent.getMediaPlayer().setRate(faktorHitrosti);
				
		}
	}
	
	public int getInputPlayer () {
		return this.inputPlayer;
	}
	
	
}
