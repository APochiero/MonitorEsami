/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorEsami;

import javafx.beans.property.*;

public class RisultatoEsame {
    private SimpleStringProperty esame;
    private SimpleDoubleProperty risultato;
    private SimpleStringProperty unitaMisura;
    private SimpleStringProperty riferimento;

    public RisultatoEsame( String esame, double risultato, String unitaMisura, String riferimento) {
        this.esame = new SimpleStringProperty(esame);
        this.risultato = new SimpleDoubleProperty(risultato);
        this.unitaMisura = new SimpleStringProperty(unitaMisura);
        this.riferimento = new SimpleStringProperty(riferimento);
    }
    
    public String getEsame() {
        return esame.get();
    }

    public void setEsame(String esame) {
        this.esame = new SimpleStringProperty(esame);
    }

    public double getRisultato() {
        return risultato.get();
    }

    public void setRisultato(double risultato) {
        this.risultato = new SimpleDoubleProperty(risultato);
    }

    public String getUnitaMisura() {
        return unitaMisura.get();
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = new SimpleStringProperty(unitaMisura);
    }

    public String getRiferimento() {
        return riferimento.get();
    }

    public void setRiferimento(String riferimento) {
        this.riferimento = new SimpleStringProperty(riferimento);
    }
    
    public boolean checkRisultato() { // 1 
        String[] riferimenti = getRiferimento().split("-");
        double min = Double.parseDouble(riferimenti[0]);
        double max = Double.parseDouble(riferimenti[1]);
        return getRisultato() >= min && getRisultato() <= max;
    }
    
    
}

/* Note:
        (1) Restituisce true solo se il risultato è compreso tra i valori di 
            riferimento
*/