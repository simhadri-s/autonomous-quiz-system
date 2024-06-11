package customlib;
import com.fazecast.jSerialComm.*;
//import java.io.IOException;
import mainC.MainP;
import customlib.dreceive;

public class dreceive {
    //private MainP mainP;
    public void recieve(MainP mainP) {

        
        // Find the Arduino port
        SerialPort arduinoPort = SerialPort.getCommPort("COM3"); // Replace with your Arduino's port name

        // Open the port
        if (arduinoPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open port.");
            return;
        }

        // Set port parameters (match with Arduino setup)
        arduinoPort.setComPortParameters(9600, 8, 1, 0); // 9600 baud rate, 8 data bits, 1 stop bit, no parity
        // Continuously read and print data
        arduinoPort.flushIOBuffers();
        while (true) {
            if (arduinoPort.bytesAvailable() > 0) {
                byte[] buffer = new byte[arduinoPort.bytesAvailable()];
                @SuppressWarnings("unused")
                int bytesRead = arduinoPort.readBytes(buffer, buffer.length);
                String data = new String(buffer);
                int idata;
                if(data!=""){
                    try{
                        idata=Integer.parseInt(data.trim().replaceAll("\\s+", ""));
                        if(idata>=1 || idata<=5){
                            System.out.println("Received data: " + data);
                            arduinoPort.closePort();
                            mainP.remoteSelect(idata);
                            break;
                        }
                    }catch (NumberFormatException e){}
            }
            }
        }

    }
    
}
