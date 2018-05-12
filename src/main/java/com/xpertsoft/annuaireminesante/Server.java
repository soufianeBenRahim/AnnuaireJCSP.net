/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpertsoft.annuaireminesante;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Soufiane
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
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
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
