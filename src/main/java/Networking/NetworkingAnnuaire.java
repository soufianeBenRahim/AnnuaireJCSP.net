package Networking;


import Metier.MetierAnnuair;
import org.jcsp.lang.*;
import org.jcsp.net2.*;
import java.util.*;
import org.jcsp.net2.tcpip.TCPIPNodeAddress;


public class NetworkingAnnuaire implements CSProcess {

    NetChannelInput chanelIn;

    Hashtable hash;
    MetierAnnuair m;
    private static final int CONNECT_SUCCES = 0;
    private static final int CONNECT_FAILER = 1;
    private static final int DESCONNECT_SUCCES = 2;
    private static final int DESCONNECT_FAILER = 3;
    private static final int USER_NOT_FOUND = 4;

    public NetworkingAnnuaire(NetChannelInput in, MetierAnnuair _m) {
        chanelIn = in;
        hash = new Hashtable();
        m = _m;
    }

    public void run() {
        while (true) {

            Object o = chanelIn.read();

            if (o instanceof Contact) {
                Contact c = (Contact) o;
                NodeID remoteID
                        = LinkFactory.getLink(c.getNodAdress()).getRemoteNodeID();
                NetChannelOutput out = NetChannel.one2net(remoteID, 46);
                switch (c.getTypeRequist()) {
                    case Contact.GETLOCATION:
                        Object Adress=hash.get(c.getPSUDO());
                        if (Adress==null){
                            out.write(USER_NOT_FOUND);
                            System.out.println("Psudo :" + c.getPSUDO()+" Not found");
                        }else{
                            out.write(new Contact(c.getPSUDO(),(TCPIPNodeAddress) Adress));
                            System.out.println("GetLocation recu de psudo :" + c.getPSUDO());
                        }

                        break;
                    case Contact.CONNECT:
                        if (m.login(c.getPSUDO(), c.getPASSWORS())) {
                            addOutputAdress(c);
                            out.write(CONNECT_SUCCES);
                        } else {
                            out.write(CONNECT_FAILER);
                        }
                        break;
                    case Contact.DISCONNECT:
                        if (m.desconnect(c.getPSUDO(), c.getPASSWORS())) {
                            removeOutputAdress(c);
                            out.write(DESCONNECT_SUCCES);
                        } else {
                            out.write(DESCONNECT_FAILER);
                        }
                        break;
                }
            }

        }
    }

    private void addOutputAdress(Contact cb) {
        System.out.println("add contact :" + cb.getPSUDO());
        hash.put(cb.getPSUDO(), cb.getNodAdress());
    }

    private void removeOutputAdress(Contact cb) {
        System.out.println("removing outputchan " + cb.getPSUDO());

        hash.remove(cb.getPSUDO());
        System.out.println("removed");
    }
}
