/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import java.io.Serializable;
import org.jcsp.lang.ChannelOutput;

/**
 *
 */
public class Contact implements Serializable {

    public Contact() {
    }
// pour la connection deconnection

    public Contact(String PSUDO, String PASSWORS, ChannelOutput returnChan, int connect) {
        this.PSUDO = PSUDO;
        this.PASSWORS = PASSWORS;
        this.returnChan = returnChan;
        this.TypeRequist = connect;
    }

// pour getLocation methode
    public Contact(String PSUDO, ChannelOutput returnChan) {
        this.PSUDO = PSUDO;
        this.PASSWORS = "";
        this.returnChan = returnChan;
        this.TypeRequist = 0;
    }

    public int getTypeRequist() {
        return TypeRequist;
    }

    public void setTypeRequist(int TypeRequist) {
        this.TypeRequist = TypeRequist;
    }
    public static final int GETLOCATION = 0;
    public static final int CONNECT = 1;
    public static final int DISCONNECT = 2;
  
    private String PSUDO;
    private String PASSWORS;
    private ChannelOutput returnChan;
    private int  TypeRequist;



    public String getPSUDO() {
        return PSUDO;
    }

    public void setPSUDO(String PSUDO) {
        this.PSUDO = PSUDO;
    }

    public String getPASSWORS() {
        return PASSWORS;
    }

    public void setPASSWORS(String PASSWORS) {
        this.PASSWORS = PASSWORS;
    }

    public ChannelOutput getReturnChan() {
        return returnChan;
    }

    public void setReturnChan(ChannelOutput returnChan) {
        this.returnChan = returnChan;
    }

}
