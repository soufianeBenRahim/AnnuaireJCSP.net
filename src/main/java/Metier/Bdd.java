/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

/**
 *
 * @author soufiane
 */
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;



public class Bdd {

    private Connection conn = null;

    public Bdd() {
    }

    public void initialiserConnexion(String fichierConfig) throws Exception {
        Properties propBD = new Properties();

        try {
            FileInputStream entree = new FileInputStream(fichierConfig);
            propBD.load(entree);
        } finally {
        }
System.out.println("Driver"+propBD.getProperty("driver"));
System.out.println("url"+propBD.getProperty("url"));
System.out.println("user"+propBD.getProperty("user"));
System.out.println("password"+propBD.getProperty("password"));
        Class.forName(propBD.getProperty("driver"));
        conn = DriverManager.getConnection(propBD.getProperty("url"),
                propBD.getProperty("user"), propBD.getProperty("password"));
    System.out.println(propBD.getProperty("url"));     
    }

    public void deconnexion() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    public PreparedStatement GetPreparedStatement(String Query) {
        PreparedStatement ps = null;
        try {
           
            ps = conn.prepareStatement(Query);
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ps;
    }


    public ResultSet ExecForUpdate(String sql) {
        try {
            Statement dbStat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            return dbStat.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
 public int getNBEnre (ResultSet rs){
 int i=0;
        try {
            rs.beforeFirst();
            while (rs.next()) {
            i++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
 return i;
 }


     public ResultSet exec(String sql) {
            
        try {
            
            Statement dbStat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            return dbStat.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean exec2(String sql) {
        try {
            java.sql.Statement dbStat = conn.createStatement();
            boolean b = dbStat.execute(sql);
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Connection GetConnection() {
        return conn;
    }

    public boolean executUpdate(String sql) {
        try {
            java.sql.Statement dbStat = conn.createStatement();
            int b = dbStat.executeUpdate(sql);
            return (b>0);
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }



}