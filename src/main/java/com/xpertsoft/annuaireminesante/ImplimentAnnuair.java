package com.xpertsoft.annuaireminesante;

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
    
    public ImplimentAnnuair() throws RemoteException{
        super();
    }
    @Override
    public boolean Login(String Name, String Pass) throws RemoteException {
        System.out.println( "Logni reussite Name "+Name+" pass "+Pass);
        
        return true;
    }

    @Override
    public String GetIPUser(String Psudo) throws RemoteException {
         System.out.println("GetIPUser "+Psudo);
         return "GetIPUser "+Psudo;
    }

    @Override
    public void Desconect(String Psudo, String Pass) throws RemoteException {
     }
    
}
