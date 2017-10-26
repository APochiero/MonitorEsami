
package monitorEsami;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.scene.chart.Axis;


public class AsseData extends Axis<Date> { //1 
    private Date lowerBound;

    public Date getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Date lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Date getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Date upperBound) {
        this.upperBound = upperBound;
    }
    private Date upperBound;
        
    @Override
    protected Object autoRange(double length) {
       return getRange();
    }

    @Override
    protected void setRange(Object range, boolean animate) {
        /**/
    }

    @Override
    protected Object getRange() {
        return new Object[]{ getLowerBound(), getUpperBound() };
    }

    @Override
    public double getZeroPosition() {
        return 0;
    }

    @Override
    public double getDisplayPosition(Date value) { 
        final double length =  getWidth();
        double diff = upperBound.getTime() - lowerBound.getTime(); 
        double range = length - getZeroPosition(); 

        double d = (value.getTime() - lowerBound.getTime()) / diff; 
 
        return d * range + getZeroPosition(); 
        
    }

    @Override
    public Date getValueForDisplay(double displayPosition) {
        final double length =  getWidth();
        double diff = upperBound.getTime() - lowerBound.getTime(); 
        double range = length - getZeroPosition(); 

        return new Date((long) ((displayPosition - getZeroPosition()) / range * diff + lowerBound.getTime())); 
    }

    @Override
    public boolean isValueOnAxis(Date value) {
       return value.getTime() > lowerBound.getTime() && value.getTime() < upperBound.getTime(); 
    }

    @Override
    public double toNumericValue(Date value) {
        return value.getTime();
    }

    @Override
    public Date toRealValue(double value) {
        return new Date((long) value);
    }

    @Override
    protected List<Date> calculateTickValues(double length, Object range) { // 2 
        
        List<Date> dateList = new ArrayList<>(); 
        Calendar upper = Calendar.getInstance();
        Calendar lower = Calendar.getInstance();
        int numberTicks = 5;
        
        upper.setTime(upperBound);
        lower.setTime(lowerBound);
        int diffYear = upper.get(Calendar.YEAR) - lower.get(Calendar.YEAR);          
        int diffMonth = diffYear*12 + upper.get(Calendar.MONTH) - lower.get(Calendar.MONTH);
        int diffDay = diffMonth*30 +  upper.get(Calendar.DATE) - lower.get(Calendar.DATE);
        int gap = diffDay/numberTicks;
       
        dateList.add(lowerBound);  
        for ( int i = 0 ; i < numberTicks; i++) {
            lower.add(Calendar.DATE, gap);
            dateList.add(lower.getTime());
        }
        
        dateList.add(upperBound );
        return dateList;
    }

    @Override
    protected String getTickMarkLabel(Date value) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
       return simpleDateFormat.format(value);
    }
    
}


/* Note: 
    (1) La classe è stata implementata prendo come riferimento il codice di questa pagina
        http://www.programcreek.com/java-api-examples/index.php?source_dir=Corendon-master/src/nl/itopia/corendon/components/DateAxis.java
        L'obiettivo è quello di estendere la classe Axis per creare un tipo di Asse per
        LineChart che utilizza Date come valori.
    (2) Calcola il range di giorni tra la data minima e massima e divide l'intervallo 
        in numberTicks, creando una data per ogni intervallo. Restituisce la lista 
        di date da visualizzare sull'asse

    
*/