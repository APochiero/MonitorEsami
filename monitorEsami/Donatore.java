package monitorEsami;

public class Donatore {

    private String email;
    private String gruppoSanguigno;
    
    public Donatore( String email, String gruppoSanguigno ){
        this.email = email;
        this.gruppoSanguigno = gruppoSanguigno;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGruppoSanguigno() {
        return gruppoSanguigno;
    }

    public void setGruppoSanguigno(String gruppoSanguigno) {
        this.gruppoSanguigno = gruppoSanguigno;
    }
    
}
