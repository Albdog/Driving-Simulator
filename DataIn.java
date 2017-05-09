//import gnu.io.*;
//import java.util.*;

//public class DataIn {
//	
//    public static void main(String arg[]) throws Exception{
//	System.out.println("Program started");
//
//	//System.out.println(java.library.path);
//	CommPortIdentifier serialPortId;
//	//static CommPortIdentifier sSerialPortId;
//	Enumeration enumComm;
//	//SerialPort serialPort;
//
//	enumComm = CommPortIdentifier.getPortIdentifiers();
//	while (enumComm.hasMoreElements()) {
//	    serialPortId = (CommPortIdentifier) enumComm.nextElement();
//	    if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//		System.out.println(serialPortId.getName());
//	    }
//	}
//
//	System.out.println("Finished successfully");
//	ArduinoReader reader = new ArduinoReader();
//
//	reader.begin();
//    }		
//}
