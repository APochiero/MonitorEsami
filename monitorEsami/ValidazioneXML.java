package monitorEsami;

import java.io.File;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;

public class ValidazioneXML {
    private String pathnameXDS;
    
    public ValidazioneXML( String pathnameXDS ) {
        this.pathnameXDS = pathnameXDS;
    }
    
    public boolean valida( String xml ) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema s = sf.newSchema( new StreamSource( new File(pathnameXDS)));
            s.newValidator().validate(  new StreamSource( new StringReader(xml))); // 1
        } catch ( Exception e ) {
            if ( e instanceof SAXException )
                System.err.println("Errore di validazione: " + e.getMessage());
            else 
                System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}


/* Note: 
        (1) La validazione è effettuta sulla stringa passa come argomento non 
            da un Documento letto dinamicamente
*/