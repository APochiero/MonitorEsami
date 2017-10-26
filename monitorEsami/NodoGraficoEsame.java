/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorEsami;

import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class NodoGraficoEsame extends StackPane {
    private final Label etichetta;
    
    public NodoGraficoEsame( Date data, double valore, double lowerBound, double upperBound, Date minDate, Date maxDate ) {
        etichetta = new Label( "Data: " + data.toString() + "\nValore: " + valore );
        etichetta.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        etichetta.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        
        posizionaNodo(data, valore, lowerBound, upperBound, minDate, maxDate);
        
        setOnMouseEntered((MouseEvent mouseEvent) -> {  // 1 
            setCursor(Cursor.HAND);
            getChildren().setAll(etichetta);
          
        });
        setOnMouseExited((MouseEvent mouseEvent) -> {
            setCursor(Cursor.DEFAULT);
            getChildren().clear();
        });
    }         
    
    public void setEtichettaRossa( String rgb ) {
        setStyle("-fx-background-color: rgb(255, 0, 20); "
                + " -fx-border-color: "+rgb+ " ;"
                + " -fx-border-radius: 100;" );
    }
    
    private void posizionaNodo( Date data, double valore, double lowerBound, double upperBound, Date minDate, Date maxDate ) {  // 2
        double range = upperBound - lowerBound;
        final long ONE_DAY = 86400000;
        if ( valore <= lowerBound + range*0.3 && data.before( new Date(minDate.getTime() + ONE_DAY*3))) 
            setAlignment(etichetta, Pos.BOTTOM_LEFT);
        else if ( valore <= lowerBound + range*0.3 && data.after( new Date(maxDate.getTime() - ONE_DAY*3)) ) 
            setAlignment(etichetta, Pos.BOTTOM_RIGHT);
        else if ( valore >= upperBound - range*0.3 && data.before( new Date(minDate.getTime() + ONE_DAY*3)) ) 
             setAlignment(etichetta, Pos.TOP_LEFT);
        else if ( valore >= upperBound - range*0.3 && data.after( new Date(maxDate.getTime() - ONE_DAY*3)) ) 
             setAlignment(etichetta, Pos.TOP_RIGHT);
        else if ( valore <= lowerBound + range*0.3 )
            setAlignment(etichetta, Pos.BOTTOM_CENTER);
        else if ( valore >= upperBound - range*0.3 )
            setAlignment(etichetta, Pos.TOP_CENTER);
        else if ( data.before( new Date(minDate.getTime() + ONE_DAY*3)) )
            setAlignment(etichetta, Pos.CENTER_LEFT);
        else if ( data.after( new Date(maxDate.getTime() - ONE_DAY*3)) )
            setAlignment(etichetta, Pos.CENTER_RIGHT);
    }
    
}


/* Note: 
        (1) L'etichetta è visibile onMouseEnter ed è nascosta onMouseExit dal nodo
        (2) Posiziona l'etichetta in modo che sia visibile indipendentemente dalla 
            posizione del nodo nel grafico. In particolare la riposiziona se il nodo
            è vicino ai margini del grafico.
*/