package monitorEsami;

import java.util.Date;

public class RisultatoEsameGrafico extends RisultatoEsame { // 1 
    private Date data;
    
    public RisultatoEsameGrafico(String esame, double risultato, String unitaMisura, String riferimento, Date data) {
         super(esame, risultato, unitaMisura, riferimento);
         this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}


/* Note: 
        (1) Estende la classe RisultatoEsame aggiungendo la data, necessaria nel 
            grafico come valore dell'asse X del nodo
*/