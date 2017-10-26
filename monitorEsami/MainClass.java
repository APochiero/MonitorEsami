
package monitorEsami;

import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainClass extends Application {
    private FiltroRicerca filtro;
    private ModuloRisultatiEsame risultati;
    private GraficoEsame grafico;
    private GestoreDatabase db;
    private GestoreCache cache;
    private GestoreLogAttivita log;
    private GestoreFileConfigurazione fileConfigurazione;
    private ConfigurazioneXMLParametri config;
    private String esameSelezionato;
    private Date primaData;
    private Date secondaData;
    private String emailDonatore;
    private String rgbSecondario;
    private String rgbBackground;
    
    @Override
    public void init() {
        filtro = new FiltroRicerca();
        risultati = new ModuloRisultatiEsame();
        grafico = new GraficoEsame();
        
        db = new GestoreDatabase();
        cache = new GestoreCache("./myfiles/bin/cache.bin");
        
        fileConfigurazione = new GestoreFileConfigurazione( "./myfiles/xml/Config.xml", "./myfiles/xsd/validazioneConfig.xsd");
        config = fileConfigurazione.leggiFileConfig();
        log = new GestoreLogAttivita(config.getIpServer(), config.getPortaServerLog(), config.getIpClient());
        log.inviaAttivita( EventoLog.Avvio);
        emailDonatore = "";
        esameSelezionato = "Nessun Esame Selezionato";
        rgbSecondario = "rgb(" + config.getColoreSecondario().getRed() +","+ config.getColoreSecondario().getGreen() + "," + config.getColoreSecondario().getBlue() + ")"; // 1
        rgbBackground = "rgb(" + config.getColoreBackground().getRed() +","+ config.getColoreBackground().getGreen() + "," + config.getColoreBackground().getBlue() + ")";
    } 
    
    @Override
    public void start(Stage primaryStage)  {
        configuraFiltro();
        configuraModuloRisultati();
        ripristinaRicerca( cache.leggiCache() ); // 2
         
        grafico.getGrafico().setPadding( new Insets(0,30,0,0));
        grafico.getGrafico().setStyle("CHART_COLOR_1: " + rgbSecondario + ";");
        VBox mainBox = new VBox(); // 3
		mainBox.setMinWidth(700);
        mainBox.setStyle("-fx-background-color:" + rgbBackground + ";");
        mainBox.setPadding( new Insets(10, 10, 10, 10));
        mainBox.getChildren().addAll(filtro, risultati, grafico.getGrafico());
        Scene scene = new Scene( mainBox );
        
        primaryStage.setOnCloseRequest( ( WindowEvent we ) -> { // 4
            log.inviaAttivita(EventoLog.Termine);
            onCloseEvent();
        });
        primaryStage.setTitle("Monitor Esami");
        primaryStage.getIcons().add( new Image("file:./myfiles/icon.png") );
        primaryStage.setScene(scene);
        primaryStage.show();
        
        filtro.setStile(config.getDimensioneFont(), rgbSecondario);
        risultati.setStile(rgbSecondario, config.getDimensioneFont()); // 5 
    }

    private void onRicercaPressed() {
        primaData = filtro.getDa();
        secondaData = filtro.getA();
        emailDonatore = filtro.getDonatore();
        Donazione donazione = db.caricaDonazione(emailDonatore, primaData , secondaData);
        if ( donazione == null ) {
            risultati.azzeraCampi();
            return;
        }
        int totaleEsami = db.getDonazioniTotali( emailDonatore, primaData, secondaData);
        risultati.setTotaleEsami(totaleEsami);
        risultati.setData(donazione.getData());
        risultati.setEsito( donazione.getEsito()? "POS": "NEG");
        risultati.setGruppoSanguigno(donazione.getDonatore().getGruppoSanguigno());
        risultati.getTabella().visualizzaRisultati( donazione.getReferto() );
    }
    
    
    private void ripristinaRicerca( DatiCache precedenteRicerca ) {
        if ( precedenteRicerca.getDonatore().equals("NOT_VALID_CACHE") )
            return;
        esameSelezionato = precedenteRicerca.getEsame();
        filtro.setDa(precedenteRicerca.getDa());
        filtro.setA(precedenteRicerca.getA());
        filtro.setDonatore(precedenteRicerca.getDonatore());
        primaData = filtro.getDa();
        secondaData = filtro.getA();
        emailDonatore = filtro.getDonatore();
             
        ArrayList<RisultatoEsameGrafico> andamentoEsame = db.caricaEsame( emailDonatore, primaData, secondaData, esameSelezionato);
        visualizzaEsame( andamentoEsame, primaData, secondaData);
        onRicercaPressed();
    }
    
    private void visualizzaEsame( ArrayList<RisultatoEsameGrafico> listaValoriEsame, Date primaData, Date secondaData ) {
        grafico.getLista().clear();
        if ( listaValoriEsame.isEmpty() ) {
            return;
        }
        grafico.init(listaValoriEsame);
        
        listaValoriEsame.stream().forEach((re) -> {   // 8
            double risultato = re.getRisultato();
            Date data = re.getData();
            XYChart.Data< Date, Double > datiNodo =  new XYChart.Data<>( data, risultato);
            NodoGraficoEsame nodo = new NodoGraficoEsame( data, risultato, grafico.getLowerBound(), grafico.getUpperBound(), grafico.getMinDate(), grafico.getMaxDate());

            if ( !re.checkRisultato() )
                nodo.setEtichettaRossa(rgbSecondario);
            
            nodo.setOnMouseClicked((MouseEvent ev ) -> {
                log.inviaAttivita(EventoLog.NodoGrafico);
                Donazione donazione = db.caricaDonazione(emailDonatore, data , data);
                if ( donazione == null ) {
                    risultati.azzeraCampi();
                    return;
                }
                int totaleEsami = db.getDonazioniTotali( emailDonatore, primaData, secondaData);
                risultati.setTotaleEsami(totaleEsami);
                risultati.setData(donazione.getData());
                risultati.setEsito( donazione.getEsito()? "POS": "NEG");
                risultati.setGruppoSanguigno(donazione.getDonatore().getGruppoSanguigno());
                risultati.getTabella().visualizzaRisultati( donazione.getReferto() );
            });
            datiNodo.setNode( nodo );
            grafico.getLista().add(datiNodo);
        });
        
    }
    
    private void configuraFiltro() {
        
        filtro.getRicerca().setOnAction( (ActionEvent ev ) -> {
            log.inviaAttivita(EventoLog.Ricerca);
            onRicercaPressed();
            visualizzaEsame(new ArrayList<>(), primaData, secondaData);
            esameSelezionato = "Nessun Esame Selezionato";
        });
        
        filtro.getAzzera().setOnAction( (ActionEvent ev ) -> {
            filtro.onAzzeraPressed();
            log.inviaAttivita(EventoLog.Azzera);
        }); 
    }
    
    private void configuraModuloRisultati() { 
                                                // 7 
        risultati.getTabella().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends RisultatoEsame> observable, RisultatoEsame oldValue, RisultatoEsame newValue) -> {
            if ( newValue != null  ) {
                log.inviaAttivita(EventoLog.RigaTabella);
                esameSelezionato = newValue.getEsame();
                ArrayList<RisultatoEsameGrafico> andamentoEsame = db.caricaEsame( emailDonatore, primaData, secondaData, esameSelezionato);
                visualizzaEsame( andamentoEsame, primaData, secondaData );
            }
        });
    }
    
    private void onCloseEvent() {
        if ( emailDonatore.equals("") )
                emailDonatore = "NOT_VALID_CACHE";
            
            primaData = filtro.getDa();
            secondaData = filtro.getA();
            DatiCache ultimaRicerca = new DatiCache(primaData, secondaData, emailDonatore, esameSelezionato);
            cache.scriviDatiCache(ultimaRicerca);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

/* Note:
        (1) Conversione da Color ad una string che rappresenta il colore in rgb 

        (2) Ripristino della ricerca effettuata prima della chiusura del programma
            tramite i dati salvati in cache

        (3) VBox che ingloba tutti gli altri elementi grafici. Su questa viene 
            applicato il colore di background specificato nel file di configurazione

        (4) Evento lanciato alla chiusura del programma. Invia l'EventoLog.Termine e
            salva i dati dell'ultima ricerca in cache

        (5) Cambia il colore della scrollbar della tabella, è necessario chiamarla 
            dopo la primaryStage.show() altrimenti la scrollbar non sarebbe ancora 
            renderizzata e si avrebbe una NullPointerException
            
        (6) Per ogni valore RisultatoEsameGrafico nell'array viene instanziato un
            NodoGraficoEsame e associato un nuovo evento onMouseClick, in modo da 
            visualizzare nella tabella dei Risultati la donazione relativa al nodo 
            cliccato.

        (7) Ogni volta che una riga della tabella viene re-renderizzata viene lancia
            questo evento che invia l'EventoLog.Rigatabella al serverLog e carica
            sul grafico i valori dell'esame selezionato.
        
*/