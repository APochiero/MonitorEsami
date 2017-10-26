package monitorEsami;

import java.io.*;
import java.net.Socket;
import java.util.Date;


public class GestoreLogAttivita {
    private String ipServerLog;
    private int portaServerLog;
    private String ipClient;
    

    public GestoreLogAttivita(String ipServerLog, int portaServerLog, String ipClient ) {
        this.ipServerLog = ipServerLog;
        this.portaServerLog = portaServerLog;
        this.ipClient = ipClient;
    }
    
    public void inviaAttivita( EventoLog evento ) {
        String now = new Date().toString();
        LogXMLAttivita nuovoRecord = new LogXMLAttivita(ipClient, "MonitorEsami", now, evento);
        try ( Socket sock = new Socket(ipServerLog, portaServerLog);
             ObjectOutputStream bout = new ObjectOutputStream(sock.getOutputStream()) ) {
            bout.writeObject(nuovoRecord); 
        } catch (IOException ex) { 
            System.err.println("Errore durante l'invio attività " + nuovoRecord.toString() + " al Server ");
        } 
    }    
}
   
