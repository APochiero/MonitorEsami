package monitorEsami;

import java.io.Serializable;
import java.util.Date;

public class DatiCache implements Serializable { 
    private Date da;
    private Date a;
    private String donatore;
    private String esame;

    public DatiCache(Date da, Date a, String donatore, String esame) {
        this.da = da;
        this.a = a;
        this.donatore = donatore;
        this.esame = esame;
    }

    public Date getDa() {
        return da;
    }

    public Date getA() {
        return a;
    }

    public String getDonatore() {
        return donatore;
    }

    public String getEsame() {
        return esame;
    }
}
