import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class ProcesnaEnota extends Thread {
	Sensor_niti2 sensor;
	public ProcesnaEnota(Sensor_niti2 sensorVhod){
		sensor = sensorVhod;
	}
	
	Integer number;
	
	DataInputStream inS;
	PrintStream osS;
	
	ServerSocket listener;
	
	
	public void run () {
		String str = "";
		
		System.out.println("Procesna enota je pripravljena. Zazeni krmilno enoto!");
	
		try {
			listener = new ServerSocket(1342);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			
			Socket l1 = listener.accept();
			inS = new DataInputStream (l1.getInputStream());
			osS = new PrintStream (l1.getOutputStream());
			System.out.println("");
		
			while (true) {
			
				str = sensor.hitrostPL + '\r';
				osS.println(str);
				
				Thread.sleep(1000);
				
			}
		}
		
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	
		finally {
			try {
				listener.close();
			}
			
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
