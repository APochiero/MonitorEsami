
package monitorEsami;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ElencoRisultatiVisuale extends TableView<RisultatoEsame> { 
    private ObservableList<RisultatoEsame> listaEsami;
   
    public ElencoRisultatiVisuale() {
        listaEsami = FXCollections.observableArrayList();
        
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setMaxHeight(250);
        
        TableColumn esame = new TableColumn("Esame");
        esame.setCellValueFactory( new PropertyValueFactory<>("esame"));
        
        TableColumn risultato = new TableColumn("Risultato");
        risultato.setCellValueFactory( new PropertyValueFactory<>("risultato"));
        
        TableColumn unitaMisura = new TableColumn("Unita' Misura");
        unitaMisura.setCellValueFactory( new PropertyValueFactory<>("unitaMisura"));
        
        TableColumn riferimento = new TableColumn("Valori Riferimento");
        riferimento.setCellValueFactory( new PropertyValueFactory<>("riferimento"));
        
        setRowFactory( row -> new TableRow<RisultatoEsame>() { // (1)
            @Override
            public void updateItem(RisultatoEsame item, boolean empty ) {
                super.updateItem(item, empty);
                if ( item == null || empty ) {
                   setStyle(""); 
                } else if ( !item.checkRisultato() ) {
                   setStyle("-fx-background-color:rgba(255, 0, 0, 0.5)" );
                } else {
                    setStyle("");
                }
            }
        }); 
        
        
        setItems(listaEsami);
        getColumns().addAll(esame, risultato, unitaMisura, riferimento);
    }
    
    public void visualizzaRisultati( ArrayList<RisultatoEsame> nuoviRisultati ) {
        listaEsami.clear();
        listaEsami.addAll(nuoviRisultati);
    }
    
    public void setStile( int dimensioneFont, String rgb ) { // 2
        lookup(".thumb").setStyle("-fx-background-color:"+rgb+";");
        lookup(".increment-arrow").setStyle("-fx-background-color:"+rgb+";");
        lookup(".decrement-arrow").setStyle("-fx-background-color:"+rgb+";");
         setStyle("-fx-font-size:" + dimensioneFont + ";");
    }
}


/* Note: 
    (1) Implento un RowFactory per controllare se il risultato è compreso tra il
        minimo il massimo dati dai Valori Di Riferimento. In caso negativo, il 
        background della riga diventa rosso

    (2) Colora il thumb e le frecce della barra di scorrimento della tabella in 
        base al colore scelto nel file config


*/ 