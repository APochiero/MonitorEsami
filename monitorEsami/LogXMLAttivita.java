package monitorEsami;

import com.thoughtworks.xstream.*;
import java.io.Serializable;


public class LogXMLAttivita implements Serializable {
    private String ipClient;
    private String nomeApplicazione;
    private String timestamp;
    private EventoLog evento;

    public LogXMLAttivita(String ipClient, String nomeApplicazione, String timestamp, EventoLog evento) {
        this.ipClient = ipClient;
        this.nomeApplicazione = nomeApplicazione;
        this.timestamp = timestamp;
        this.evento = evento;
    }
    
    public LogXMLAttivita( String xml ) {
        LogXMLAttivita l = (LogXMLAttivita) new XStream().fromXML(xml);
        this.ipClient = l.ipClient;
        this.nomeApplicazione = l.nomeApplicazione;
        this.timestamp = l.timestamp;
        this.evento = l.evento;
    }
       
    @Override
    public String toString() {
        return ( new XStream()).toXML(this);
    }
}
