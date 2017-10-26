package monitorEsami;

import java.io.IOException;
import java.nio.file.*;

public class GestoreFileConfigurazione {
    private ConfigurazioneXMLParametri parametri;
    private String pathnameFileConfig;
    private ValidazioneXML validatoreConfig;
    
    public GestoreFileConfigurazione( String pathnameFileConfig, String pathnameXSD ){
        parametri = null;
        this.pathnameFileConfig = pathnameFileConfig;
        validatoreConfig = new ValidazioneXML(pathnameXSD);
    }
    
    public ConfigurazioneXMLParametri leggiFileConfig() {
        if ( parametri == null ) {
            try {
                String xml = new String( Files.readAllBytes(Paths.get(pathnameFileConfig)));
                if ( validatoreConfig.valida(xml) )
                    parametri = new ConfigurazioneXMLParametri(xml);
                else 
                    System.err.println( xml + " non valido");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            } 
        }
        return parametri;
    }  
}
