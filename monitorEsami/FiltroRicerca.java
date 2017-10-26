
package monitorEsami;

import java.time.*;
import java.util.Date;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.*;


public class FiltroRicerca extends VBox {
    private DatePicker daPicker = new DatePicker();
    private DatePicker aPicker = new DatePicker();
    private TextField donatore;
    private Button ricerca;
    private Button azzera;

    
    
    public FiltroRicerca() { // 0
        init();
        final Label titolo = new Label( "Filtro Ricerca ");
        titolo.setStyle("-fx-font-style: italic; \n"
                         + " -fx-margin-left: 5px;");
        final Label labelDonatore = new Label("Donatore");
        final Label labelDa = new Label("Da");
        final Label labelA = new Label("A");
        
        HBox primaRiga = new HBox();
        primaRiga.setSpacing( 20 );
        primaRiga.setStyle( "-fx-padding: 5px");
        primaRiga.setAlignment(Pos.CENTER);
        
        HBox secondaRiga = new HBox();
        secondaRiga.setStyle( "-fx-padding: 5px");
        secondaRiga.setSpacing( 12 );
        secondaRiga.setAlignment(Pos.CENTER);
        
        primaRiga.getChildren().addAll( labelDonatore, donatore );
        secondaRiga.getChildren().addAll( labelDa, daPicker, labelA, aPicker, ricerca, azzera );
               
        final Callback<DatePicker, DateCell> dayCellFactory = disabilitaDateNonDisponibili();
        
        daPicker.setDayCellFactory(dayCellFactory);
        aPicker.setDayCellFactory(dayCellFactory);
        getChildren().addAll(titolo, primaRiga, secondaRiga);
        
        setPadding( new Insets(10, 10, 10, 10));
    }
    
    private Callback<DatePicker, DateCell> disabilitaDateNonDisponibili() { // 1 
        DatePicker today = new DatePicker();
        today.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isAfter( today.getValue() )) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: lightgrey;");
                            }   
                        }
                    };
                }
            };
        return dayCellFactory;
    }
    
    public void onAzzeraPressed() {
        daPicker.setValue( LocalDate.now() );
        aPicker.setValue( LocalDate.now() );
        donatore.setText("");
    }
    
    private void init() {
        daPicker = new DatePicker();
        daPicker.setValue(LocalDate.now());
        daPicker.setMaxWidth(150);
        aPicker = new DatePicker();
        aPicker.setValue(LocalDate.now());
        aPicker.setMaxWidth(150);
        donatore = new TextField(); 
        donatore.setPromptText("andrea.rossi@gmail.com");
        donatore.setMinWidth( 500 );
        ricerca = new Button("Ricerca");
        ricerca.setStyle("-fx-font-size: 20");
        azzera = new Button("Azzera");
        azzera.setStyle("-fx-font-size: 20");
    }

    public void setDa(Date da) {
        daPicker.setValue( da.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()  ); // 2 
    }

    public void setA(Date a) {
        aPicker.setValue( a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()  ); // 2
    }
    
    public void setDonatore(String donatore) {
        this.donatore.setText(donatore);
    }

    public Date getDa() {
        return Date.from(daPicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()); // 3
    }

    public Date getA() {
        return Date.from(aPicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()); // 3
    }

    public Button getRicerca() {
        return ricerca;
    }

    public Button getAzzera() {
        return azzera;
    }
    
    public String getDonatore() {
        return donatore.getText();
    }
    
    public void setStile( int dimensioneFont, String rgb) {
        setStyle( " -fx-border-style: solid; \n"
                + " -fx-border-insets: 5; \n"
                + " -fx-border-width: 2; \n "
                + " -fx-border-color: "+ rgb + "; \n"
                + " -fx-font-size:" + dimensioneFont + "; \n");
    }
}


/* Note: 
        (0) Dispone gli elementi su due righe definite da 2 HBox 
        (1) Restituisce DateCell disabilitate se la data è superiore alla data odierna.
            Implementata prendendo come riferimento il codice all pagina 
            https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm#CCHEBIFF

        (2) Conversione da Date a LocalDate
        (3) Conversione da LocalDate a Date
*/