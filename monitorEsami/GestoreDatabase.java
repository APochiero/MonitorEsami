package monitorEsami;

import java.sql.*;
import java.util.*;

public class GestoreDatabase {
    
    private static Connection connessione;
    private static PreparedStatement selectUltimaDonazione;
    private static PreparedStatement selectDonazioniTotali;
    private static PreparedStatement selectEsame;
    
    
    
    static {
        try {
            connessione = DriverManager.getConnection( "jdbc:mysql://localhost:3306/monitorEsami" , "root", "");
            selectDonazioniTotali = connessione.prepareStatement( "SELECT COUNT(idDonazione) AS DonazioniTotali"  // 1 
                    + " FROM Donazione INNER JOIN Donatore ON donatore = email "
                    + " WHERE donatore = ? AND"
                    + " data BETWEEN ? AND ? ");
            
            selectUltimaDonazione = connessione.prepareStatement("SELECT * "  // 2 
                    + " FROM Donazione INNER JOIN Donatore ON donatore = email"
                    + " INNER JOIN RisultatoEsame ON donazione = idDonazione"
                    + " WHERE donatore = ? AND "
                    + " data BETWEEN ? AND ?"
                    + " ORDER BY data DESC "
                    + " LIMIT 25");
            
            selectEsame = connessione.prepareStatement("SELECT Donatore, Esame, Risultato, RiferimentoMin, RiferimentoMax, Data, UnitaMisura" // 3 
                    + " FROM Donazione INNER JOIN Donatore ON donatore = email"
                    + " INNER JOIN RisultatoEsame ON donazione = idDonazione"
                    + " WHERE `donatore` = ? AND `esame` = ? AND Data BETWEEN ? AND ?"
                    + " ORDER BY data ASC");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public Donazione caricaDonazione( String donatore, java.util.Date da, java.util.Date a ) {
        Donazione d = null;
        ArrayList<RisultatoEsame> referto = new ArrayList<>(); 
        try {
            selectUltimaDonazione.setString(1, donatore);
            java.sql.Date daSQL = new java.sql.Date( da.getTime() ); // 4
            java.sql.Date aSQL = new java.sql.Date( a.getTime() ); 
            selectUltimaDonazione.setDate(2, daSQL);
            selectUltimaDonazione.setDate(3, aSQL);
            ResultSet resultUD = selectUltimaDonazione.executeQuery();            
            while( resultUD.next() ) {
                if ( resultUD.isFirst())
                    d = new Donazione( resultUD.getDate("data"), new Donatore( resultUD.getString("donatore"), resultUD.getString("gruppoSanguigno")) );
                RisultatoEsame re =  new RisultatoEsame( resultUD.getString("esame"), resultUD.getDouble("risultato"), resultUD.getString("unitaMisura"), resultUD.getDouble("riferimentoMin") + " - " + resultUD.getDouble("riferimentoMax"));
                referto.add(re);
            }
            if ( d != null )
                d.aggiungiReferto( referto );  
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return d;
    }

    public int getDonazioniTotali( String donatore, java.util.Date da, java.util.Date a ) {
        int donazioniTotali = 0;
        try {
            selectDonazioniTotali.setString(1, donatore);
            java.sql.Date daSQL = new java.sql.Date( da.getTime() ); 
            java.sql.Date aSQL = new java.sql.Date( a.getTime() ); 
            selectDonazioniTotali.setDate(2, daSQL);
            selectDonazioniTotali.setDate(3, aSQL);
            
            ResultSet resultDT = selectDonazioniTotali.executeQuery();
            if ( resultDT.next() )
                donazioniTotali = resultDT.getInt("DonazioniTotali");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return donazioniTotali;
    }
    
    public ArrayList<RisultatoEsameGrafico> caricaEsame( String donatore,java.util.Date da, java.util.Date a, String esame ) {
        ArrayList<RisultatoEsameGrafico> andamentoEsame = new ArrayList<>();
        try {
           selectEsame.setString(1, donatore);
           selectEsame.setString(2, esame);
           java.sql.Date daSQL = new java.sql.Date( da.getTime() ); 
           java.sql.Date aSQL = new java.sql.Date( a.getTime() ); 
           selectEsame.setDate(3, daSQL);
           selectEsame.setDate(4, aSQL);
           ResultSet resultE = selectEsame.executeQuery();
           while( resultE.next() ) {
               andamentoEsame.add( new RisultatoEsameGrafico( resultE.getString("esame"), resultE.getDouble("risultato"), resultE.getString("unitaMisura"), resultE.getDouble("riferimentoMin") + " - " + resultE.getDouble("riferimentoMax"), resultE.getDate("data")));
           }
       } catch (SQLException ex) {
            System.err.println(ex.getMessage());
       }
       return andamentoEsame;
    }
    
}

/* Note:
        (1) Seleziona nel DB il numero di prelievi effettuati nel periodo di date scelte
        (2) Seleziona nel DB i dati relativi all'ultima donazione nel periodo di date
            scelte. Seleziona tutte le donazione, ordinandole per data in ordine decrescente
            e seleziona le prime 25 ( numero di esami presenti un'analisi ) righe 
        (3) Seleziona i valori di un esame di tutte le donazione effettuate durante il 
            periodo. 
        (4) PreparedStatement necessita di Date appartenenti al package java.sql quindi è 
            è necessario convertire da java.util.Date a java.sql.Date

*/
