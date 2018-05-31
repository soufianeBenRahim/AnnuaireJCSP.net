package com.xpertsoft.annuaireminesante;

import Metier.MetierAnnuair;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 *
 * @author Soufiane
 */
public class ImplimentAnnuair {
private MetierAnnuair Metier;
public ImplimentAnnuair() throws RemoteException{
        super();
    }
       public ImplimentAnnuair(MetierAnnuair _Metier) throws RemoteException{
        super();
        Metier=_Metier;
    }

    public boolean Login(String Name, String Pass) throws RemoteException {
        System.out.println( "Logni Name "+Name+" pass "+Pass+" ressue");
        return Metier.login(Name, Pass);
    }


    public String GetIPUser(String Psudo) throws RemoteException {
         System.out.println("GetIPUser "+Psudo);
         
         return Metier.GetIPAdress(Psudo);
    }


    public boolean Desconect(String Psudo, String Pass) throws RemoteException {
     System.out.println("desconnect user : "+Psudo+"  pass "+Pass);
    return Metier.desconnect(Psudo, Pass);
    }
    
}
