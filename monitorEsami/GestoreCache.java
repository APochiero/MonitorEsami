
package monitorEsami;

import java.io.*;

public class GestoreCache {
    private String pathnameFileCache;
    
    public GestoreCache( String pathnameFileCache ) {
        this.pathnameFileCache = pathnameFileCache;
    }
    
    public DatiCache leggiCache() {
        DatiCache d = null;
        try ( FileInputStream fin = new FileInputStream( new File(pathnameFileCache));
              ObjectInputStream bin = new ObjectInputStream(fin)) {
            d = (DatiCache) bin.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return d;
    }
    
    public void scriviDatiCache( DatiCache dati ) {
        try ( FileOutputStream fout = new FileOutputStream( new File(pathnameFileCache));
                ObjectOutputStream bout = new ObjectOutputStream(fout)) {
            bout.writeObject(dati);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
