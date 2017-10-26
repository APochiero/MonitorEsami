package monitorEsami;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServerLogAttivita {
    
    private static int porta;
    private static String pathnameLog;
    private static ValidazioneXML validatoreLog;
    
    private static void scriviSuFileLog( LogXMLAttivita attivita ) {
        try {
            final String meta = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            String log = new String(Files.readAllBytes(Paths.get(pathnameLog)));
            String xml = log + "\n" + meta +  attivita.toString();
            Files.write( Paths.get( pathnameLog ), xml.getBytes());
        } catch ( IOException ex ) {
            System.err.println("File non trovato" + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        final String meta = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        validatoreLog = new ValidazioneXML("./myfiles/xsd/validazioneLog.xsd");
        porta = Integer.parseInt(args[0]);
        pathnameLog = "./myfiles/xml/Log.xml";        
        while( true ) {
            LogXMLAttivita nuovoRecord = null;
            try ( ServerSocket serv = new ServerSocket(porta); 
                  Socket sock = serv.accept(); 
                  ObjectInputStream bin = new ObjectInputStream(sock.getInputStream())) {
                nuovoRecord = (LogXMLAttivita) bin.readObject();  
                System.out.println(meta + nuovoRecord.toString() + "\n");
                if ( validatoreLog.valida( nuovoRecord.toString()) )
                    scriviSuFileLog( nuovoRecord );
                else 
                    System.err.println( nuovoRecord +  " non valido ");
            }   catch (IOException ex) {
                System.err.println("Errore durante la ricezione dell'attività");
            }   catch (ClassNotFoundException ex) {
                 System.err.println(ex.getMessage());
            }
        }
    }
}
