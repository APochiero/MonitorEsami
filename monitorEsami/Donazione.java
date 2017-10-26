package monitorEsami;

import java.util.*;

public class Donazione {

    private Date data;
    private Donatore donatore;
    private ArrayList<RisultatoEsame> referto;
    
    public Donazione(Date data, Donatore donatore) {
        this.data = data;
        this.donatore = donatore;
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Donatore getDonatore() {
        return donatore;
    }

    public void setDonatore(Donatore donatore) {
        this.donatore = donatore;
    }

    public ArrayList<RisultatoEsame> getReferto() {
        return referto;
    }
    
    public void aggiungiReferto( ArrayList<RisultatoEsame> referto ) {
        this.referto = referto;
    }
    
    public boolean getEsito() {  // 1 
        if (!referto.stream().noneMatch((re) -> ( !re.checkRisultato() ))) 
            return false;
        return true;
    }
}


/* Note: 
   (1)  Scorre tutto l'array referto richiamando la funzione checkRisultato di RisultatoEsame.
        Se qualche risultato è negativo, restituisce false.
    
*/