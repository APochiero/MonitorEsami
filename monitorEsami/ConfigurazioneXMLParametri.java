
package monitorEsami;

import com.thoughtworks.xstream.XStream;
import java.io.Serializable;
import javafx.scene.paint.Color;

public class ConfigurazioneXMLParametri implements Serializable {
    private Color coloreBackground;
    private Color coloreSecondario;
    private int dimensioneFont;
    private String ipClient;
    private String ipServer;
    private int portaServerLog;

    public ConfigurazioneXMLParametri(Color coloreBackground, Color coloreSecondario, int dimensioneFont, String ipClient, String ipServer, int portaServerLog) {
        this.coloreBackground = coloreBackground;
        this.coloreSecondario = coloreSecondario;
        this.dimensioneFont = dimensioneFont;
        this.ipClient = ipClient;
        this.ipServer = ipServer;
        this.portaServerLog = portaServerLog;
    }
    
    public ConfigurazioneXMLParametri(String xml) {
        ConfigurazioneXMLParametri c = (ConfigurazioneXMLParametri) new XStream().fromXML(xml);
        this.coloreBackground = c.coloreBackground;
        this.coloreSecondario = c.coloreSecondario;
        this.dimensioneFont = c.dimensioneFont;
        this.ipClient = c.ipClient;
        this.ipServer = c.ipServer;
        this.portaServerLog = c.portaServerLog;
    }

    public Color getColoreBackground() {
        return coloreBackground;
    }

    public Color getColoreSecondario() {
        return coloreSecondario;
    }

    public int getDimensioneFont() {
        return dimensioneFont;
    }

    public String getIpClient() {
        return ipClient;
    }

    public String getIpServer() {
        return ipServer;
    }

    public int getPortaServerLog() {
        return portaServerLog;
    }
    
    @Override
    public String toString() {
        return new XStream().toXML(this);
    }
   
}
