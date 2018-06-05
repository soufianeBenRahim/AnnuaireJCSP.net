package Networking;

import java.io.Serializable;
import org.jcsp.net2.tcpip.TCPIPNodeAddress;



/**
 *
 */
public class Contact implements Serializable {

    public Contact() {
    }
// pour la connection deconnection

    public Contact(String PSUDO, String PASSWORS, TCPIPNodeAddress returnChan, int connect) {
        this.PSUDO = PSUDO;
        this.PASSWORS = PASSWORS;
        this.NodAdress = returnChan;
        this.TypeRequist = connect;
    }

// pour getLocation methode
    public Contact(String PSUDO, TCPIPNodeAddress returnChan) {
        this.PSUDO = PSUDO;
        this.PASSWORS = "";
        this.NodAdress = returnChan;
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
    private TCPIPNodeAddress NodAdress;
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

    public TCPIPNodeAddress getNodAdress() {
        return NodAdress;
    }

    public void setNodAdress(TCPIPNodeAddress NodAdress) {
        this.NodAdress = NodAdress;
    }

}
