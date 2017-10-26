package monitorEsami;

import java.util.ArrayList;
import java.util.Date;
import javafx.collections.*;
import javafx.scene.chart.*;

public class GraficoEsame {
    private ObservableList<XYChart.Data<Date, Double>> lista;
    private LineChart grafico;
    private NumberAxis asseY;
    private AsseData asseX;
    private XYChart.Series serie;
    private double lowerBound;
    private double upperBound;
    private Date minDate;
    private Date maxDate;
   
    public GraficoEsame() {
        asseX = new AsseData();
        asseY = new NumberAxis();
        lista = FXCollections.observableArrayList();
        grafico = new LineChart(asseX, asseY);
        grafico.setTitle("Andamento Esame");
        asseX.setLabel("Data Prelievo");  
        asseX.setAutoRanging(false);
        asseX.setLowerBound(new Date());
        asseX.setUpperBound(new Date());
        asseY.setAutoRanging(false);
        serie = new XYChart.Series();
        serie.setData(lista);
        serie.setName("-");
        grafico.getData().add(serie);
        grafico.setAnimated(false);
        grafico.setHorizontalGridLinesVisible(true);
    }
    
    public void setUnitaMisura( String unitaMisura ) {
        asseX.setLabel(unitaMisura);
    }

    public ObservableList<XYChart.Data<Date, Double>> getLista() {
        return lista;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }
    
    public void init(ArrayList<RisultatoEsameGrafico> listaValoriEsame) {
        final long ONE_DAY = 86400000;
        final int NUMERO_TICK = 10;
        RisultatoEsame primoRisultato = listaValoriEsame.get(0);
        double rifMax; 
        double rifMin;
        minDate = new Date();
        maxDate = new Date(0);
        
        String[] riferimento = primoRisultato.getRiferimento().split("-");
        double max = 0;
        double min = primoRisultato.getRisultato();
        for ( RisultatoEsameGrafico re: listaValoriEsame ) { // 1 
            max = max > re.getRisultato()? max: re.getRisultato();
            min = min < re.getRisultato()? min: re.getRisultato();
            maxDate = re.getData().after(maxDate)? re.getData(): maxDate;
            minDate = re.getData().before(minDate)? re.getData(): minDate;
        }
        rifMax = Double.parseDouble(riferimento[1]);
        rifMin = Double.parseDouble(riferimento[0]);
        upperBound = max > rifMax ? max : rifMax; 
        lowerBound = min < rifMin ? min : rifMin;
              
        asseY.setLowerBound(lowerBound );
        asseY.setUpperBound(upperBound );
                
        asseX.setLowerBound( new Date( minDate.getTime() - ONE_DAY ) ); //2
        asseX.setUpperBound( new Date( maxDate.getTime() + ONE_DAY ) );
        double range = upperBound - lowerBound;
        double tick = (range/NUMERO_TICK);
        asseY.setTickUnit(tick);
        asseY.setLabel(primoRisultato.getUnitaMisura());
        serie.setName(listaValoriEsame.get(0).getEsame());
    }
    
    public LineChart getGrafico() {
        return grafico;
    }
}



/* Note: 
        (1) Ricerca dei minimi e dei massimi sia tra i risultati sia tra le date
        (2) Alle date degli estremi viene tolto/aggiunto un giorno per fare rientrare 
            il nodo all'interno del grafico
*/