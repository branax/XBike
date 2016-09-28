import java.text.DecimalFormat;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


//senzor priklopim na pin1 (3,3 VDC Power) in na pin 13 (GPIO 2)

public class Sensor_niti2 extends Thread {

	
    int stevec = 0;
    long start = 0;
    long end = 0;
    long razlika = 0; // cas enega obrata
    int indexCasi = 0; // indeks za sprehajanje po polju casi            
    int premer = 258*3; // premer platisca v mm; faktor 3 je zaradi potrebne manjse frekvence vrtenja kolesa
    double obseg = (double) (premer * 3.1415926); // obseg v mm
    double obsegM = obseg / 1000; // obseg v metrih
    public float hitrost = 0; // hitrost potovanja v m/s
    public String hitrostPL = ""; // prilagojena hitrost potovanja v m/s
        
    
    	
	public void run() {
		
		final GpioController gpio = GpioFactory.getInstance();

		final GpioPinDigitalInput stikalo = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
	
		
		for (;;) {
			
			stikalo.addListener(new GpioPinListenerDigital() {
	            
	    		long[] casi = new long [] {0,0,0,0,0,0,0,0,0,0};
	                    
	    		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	    		
	        	
	    			start = System.nanoTime(); // zapisi cas, ko se zgodi dogodek (v nanosekundah)
	               	
	    			stevec = stevec + 1; // povecaj stevec obratov za 1
	        	
	    			indexCasi = stevec % 10;// fukcija modulo za sprehajanje po polju
	        	
	    			casi[indexCasi] = start; // v polje casi na mesto stevec zapisi cas dogodka
	        	
	    			if (stevec < 2) { // v prvem koraku nimamo nicesar za odsteti
	        		}
	        	
	    			else {
	    				if (indexCasi == 0) { // ce smo v polju casi na indexCasi = 0, odstevamo cas na indexu 8
	    					razlika = casi[indexCasi] - casi[8];
	    					hitrost = (float) (obsegM*1000000000 / razlika); // pomnozimo z 1000000000, da iz nanosekund dobimo sekunde
						}
	        		
	    				else if (indexCasi == 1) { // // ce smo v polju casi na indexCasi = 1, odstevamo cas na indexu 9
	    					razlika = casi[indexCasi] - casi[9];
	    					hitrost = (float) (obsegM*1000000000 / razlika); // pomnožimo z 1000000000, da iz nanosekund dobimo sekunde
						}
	        		
	    				else { // drugace odstevamo cas na dveh mestih prej
	    					razlika = casi[indexCasi] - casi[indexCasi-2];
	    					hitrost = (float) (obsegM*1000000000 / razlika); // pomnožimo z 1000000000, da iz nanosekund dobimo sekunde
						}
					}
	    			
	    			if (hitrost < 0.1) {
	    				hitrost = (float) 0; // iznicenje racunske napake, ki je v E-4
	    			}
	    			
	    			else if (hitrost > 4) {
	    				hitrost = (float) 4; // omejimo najvisjo hitrost vrtenja, ki jo krmilna enota se zmore prikazati
	    			}
	    			
	    			else {
	    			}
	    			
	    			hitrost = hitrost * 10; // zaradi prenosa dovolj velikega stevila v krmilno enoto
	    			
	    			// zaokorzitev na 3 decimalke
	    			DecimalFormat df = new DecimalFormat();
	    			df.setMaximumFractionDigits(3);
	    			
	    			hitrostPL = df.format((float) (hitrost));
	    			
	    			
	    			System.out.println("Hitrost: " + hitrost);
	    			System.out.println("HitrostPL: " + hitrostPL);
	    			
	    			
	    			System.out.println();
	    			
	    		        	            	
				}
	            
			});
			
			
			try {
				Thread.sleep(5000);
			}
			
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
		
			        
        