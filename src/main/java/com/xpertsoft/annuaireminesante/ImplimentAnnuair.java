package com.xpertsoft.annuaireminesante;

import Metier.Bdd;
import Metier.MetierAnnuair;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Soufiane
 */
public class ImplimentAnnuair  extends UnicastRemoteObject implements IAnnuair{
private MetierAnnuair Metier;
public ImplimentAnnuair() throws RemoteException{
        super();
    }
       public ImplimentAnnuair(MetierAnnuair _Metier) throws RemoteException{
        super();
        Metier=_Metier;
    }
    @Override
    public boolean Login(String Name, String Pass) throws RemoteException {
        System.out.println( "Logni Name "+Name+" pass "+Pass+" ressue");
        System.out.println(" le nom trouvee est :"+Metier.login(Name, Pass));
        return true;
    }

    @Override
    public String GetIPUser(String Psudo) throws RemoteException {
         System.out.println("GetIPUser "+Psudo);
         
         return Metier.GetIPAdress(Psudo);
    }

    @Override
    public boolean Desconect(String Psudo, String Pass) throws RemoteException {
     System.out.println("desconnect user : "+Psudo+"  pass "+Pass);
    return Metier.desconnect(Psudo, Pass);
    }
    
}
