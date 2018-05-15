/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpertsoft.annuaireminesante;

import Metier.Bdd;
import Metier.MetierAnnuair;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Soufiane
 */
public class Server {
static private Bdd gestionBdd = new Bdd();
  static private JFileChooser bddChooser = new JFileChooser(".");
   static  private FileFilter datFilter = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            // TODO code application logic here
            
        try{        
        bddChooser.addChoosableFileFilter(datFilter);
        int status = bddChooser.showDialog(null, "Sélection du fichier de configuration de la base");
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = bddChooser.getSelectedFile();
            gestionBdd.deconnexion();
            gestionBdd.initialiserConnexion(file.getAbsolutePath());
            MetierAnnuair Metier=new MetierAnnuair(gestionBdd);
            System.out.println("demarage de service annuaire ...");
            LocateRegistry.createRegistry(1099);
            ImplimentAnnuair Annuair=new ImplimentAnnuair();
            Naming.rebind("rmi://localhost:1099/Annuair", Annuair);
            System.out.println(Annuair.toString());
            Scanner scn = new Scanner(System.in);
            System.out.println("Entre Exit pour terminer");
            while(scn.hasNext()){
                String commande = scn.next();
                if(commande.equals("Exit")) {
                    Naming.unbind("rmi://localhost:1099/Annuair");
                    return;
                }
            }
        }else{
        System.out.println("fin de programme il faut sellectionner un fichier de connfiguration SVP !");
        }
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
