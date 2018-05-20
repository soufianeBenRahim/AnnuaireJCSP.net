/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Soufiane
 */
public class MetierAnnuair {

    private Bdd GestionBdd;

    public MetierAnnuair(Bdd _GestionBdd) {
        GestionBdd = _GestionBdd;
    }
    public  boolean login(String Psudo,String Pass) {
        ResultSet rs = GestionBdd.exec("SELECT * FROM NBUSER.CONTACTS where PSUDO='" + Psudo + "' "+"and PASSWORS='"+Pass+"'");
        try {
            if(rs.equals(null)) return false;
            rs.beforeFirst();
            while (rs.next()) {
                GestionBdd.exec2("update NBUSER.CONTACTS set CONECTED=true where PSUDO='"+Psudo+"' and PASSWORS='"+Pass+"'");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MetierAnnuair.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public  String GetIPAdress(String Psudo) {
        ResultSet rs = GestionBdd.exec("SELECT * FROM NBUSER.CONTACTS where PSUDO='" + Psudo + "'");
        try {
            rs.beforeFirst();
            while (rs.next()) {
                return rs.getString("IP");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MetierAnnuair.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean desconnect(String psudo, String Pass){
        return GestionBdd.executUpdate("update NBUSER.CONTACTS set CONECTED=false where PSUDO='"+psudo+"' and PASSWORS='"+Pass+"'");
    }
    
}
