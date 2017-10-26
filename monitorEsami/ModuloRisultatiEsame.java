package monitorEsami;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ModuloRisultatiEsame extends VBox {
    private ElencoRisultatiVisuale tabella;
    private TextField dataValue;
    private TextField gruppoSanguignoValue;
    private TextField esitoValue;
    private Label totaleEsami;
    
    
    public ModuloRisultatiEsame() {
        tabella = new ElencoRisultatiVisuale();
        dataValue = new TextField();
        gruppoSanguignoValue = new TextField();
        esitoValue = new TextField();
        totaleEsami = new Label("Totale Esami: -");
        
        final Label titolo = new Label("Risultati Esami");
        titolo.setStyle("-fx-font-style: italic");
        final Label data = new Label("Data");
        final Label gruppoSanguigno = new Label("Gruppo Sanguigno");
        final Label esito = new Label("Esito");
        HBox header = new HBox(6); // 1 
        dataValue.setEditable(false);
        dataValue.setMaxWidth(120);
        dataValue.setPromptText("-/-/-");
        dataValue.setAlignment(Pos.CENTER);
        gruppoSanguignoValue.setEditable(false);
        gruppoSanguignoValue.setMaxWidth(80);
        gruppoSanguignoValue.setPromptText("-");
        esitoValue.setEditable(false);
        esitoValue.setMaxWidth(80);
        esitoValue.setPromptText("-");
        
        header.setSpacing(15);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10, 10, 10, 10));
        header.getChildren().addAll(data, dataValue, gruppoSanguigno, gruppoSanguignoValue, esito, esitoValue);
        getChildren().addAll(titolo, header, tabella, totaleEsami);
        setPadding(new Insets(10, 10, 10, 10));
    }
    
    public void setData( Date data ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dataValue.setText( simpleDateFormat.format(data) );
    }
    
    public void setGruppoSanguigno( String gruppoSanguigno ) {
        this.gruppoSanguignoValue.setText(gruppoSanguigno);
    }
    
    public void setEsito( String esito ) {
        if ( esito.equals("POS") ) 
            esitoValue.setStyle("-fx-text-fill: green");
        else 
            esitoValue.setStyle("-fx-text-fill: red");
        this.esitoValue.setText( esito );
    }
    
    public void setTotaleEsami( int totaleEsami ) {
       this.totaleEsami.setText( "Totale Esami: " + Integer.toString(totaleEsami));
    }
    
    public ElencoRisultatiVisuale getTabella() {
        return tabella;
    }

    public void azzeraCampi() {
       dataValue.setText("-/-/-");
       gruppoSanguignoValue.setText("");
       esitoValue.setText("-");
       totaleEsami.setText("Totale Esami: - ");
       tabella.visualizzaRisultati( new ArrayList<>());
    }
    
    public void setStile( String rgb, int dimensioneFont ) {
        setStyle(" -fx-border-style: solid; \n"
                + " -fx-border-insets: 5; \n"
                + " -fx-border-width: 2; \n "
                + " -fx-border-color: "+ rgb + "; \n");
        tabella.setStile(dimensioneFont, rgb);
        
    }
}

/* Note:
        (1) Posiziona i campi al di sopra della tabella su una riga
*/