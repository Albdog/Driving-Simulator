/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import gnu.io.*;
import java.io.*;

/**
 *
 * @author Albdog
 */
public class ArduinoReader implements SerialPortEventListener{
    

    /** Serial port */
    SerialPort serialPort;
    
    /** Streams */
    private InputStream    serialIn;
    private BufferedReader serialReader;
    static String line;
    
    public static double sonarSpeed;
    public static int potentialVal;
    /*
     * Connecting to port.
     */
    public void begin() throws Exception{
	// Open port
	CommPortIdentifier port = CommPortIdentifier.getPortIdentifier("COM6");
        CommPort commPort = port.open(this.getClass().getName(),2000);
        serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	serialIn=serialPort.getInputStream();
	serialReader = new BufferedReader( new InputStreamReader(serialIn) );
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    }

    /*
     * Reads input from Serial.
     */
    @Override
    public void serialEvent(SerialPortEvent spe) {
	try {
	    line = serialReader.readLine();

	    if(line.startsWith("SS:")) {
                potentialVal = potentiometer();
	    } else if(line.startsWith("dis:")) {
		sonarSpeed = sonar();
	    }
	} catch (IOException ex) {
	    System.out.println("You fucked up.");
	}
    }
    
    /*
     * Converts hex input from Potentiometer to decimal.
     * Returns converted input.
     * Input from this will be 0x000 to 0x3FF
     * 0x000 is potentiometer rotated to left
     * 0x3FF is potentiometer rotated to right
     */
    public static int potentiometer() {
	String[] values = line.split(":");
	
	String hex = values[1]; //need to convert hex to decimal
	
	return Integer.parseInt(hex, 16); //replace 0 with converted value
    }
    
    /*
     * Returns distance from sonar.
     */
    public static double sonar() {
	String[] values = line.split(":");
	
	double distance = Double.parseDouble(values[1]);
	
	return distance;
    }
}

