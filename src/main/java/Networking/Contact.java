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

    public Contact(String PSUDO, String PASSWORS, ChannelOutput returnChan, boolean connect) {
        this.PSUDO = PSUDO;
        this.PASSWORS = PASSWORS;
        this.returnChan = returnChan;
        this.connect = connect;
    }

// pour getLocation methode
    public Contact(String PSUDO, ChannelOutput returnChan) {
        this.PSUDO = PSUDO;
        this.PASSWORS = "";
        this.returnChan = returnChan;
        this.connect = true;
    }
    private String PSUDO;
    private String PASSWORS;
    private ChannelOutput returnChan;
    private boolean connect;

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

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
