package Networking;

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  JCSP ("CSP for Java") Libraries                                 //
//  Copyright (C) 1996-2008 Peter Welch and Paul Austin.            //
//                2001-2004 Quickstone Technologies Limited.        //
//                                                                  //
//  This library is free software; you can redistribute it and/or   //
//  modify it under the terms of the GNU Lesser General Public      //
//  License as published by the Free Software Foundation; either    //
//  version 2.1 of the License, or (at your option) any later       //
//  version.                                                        //
//                                                                  //
//  This library is distributed in the hope that it will be         //
//  useful, but WITHOUT ANY WARRANTY; without even the implied      //
//  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR         //
//  PURPOSE. See the GNU Lesser General Public License for more     //
//  details.                                                        //
//                                                                  //
//  You should have received a copy of the GNU Lesser General       //
//  Public License along with this library; if not, write to the    //
//  Free Software Foundation, Inc., 59 Temple Place, Suite 330,     //
//  Boston, MA 02111-1307, USA.                                     //
//                                                                  //
//  Author contact: P.H.Welch@kent.ac.uk                             //
//                                                                  //
//                                                                  //
//////////////////////////////////////////////////////////////////////
import Metier.MetierAnnuair;
import org.jcsp.lang.*;
import org.jcsp.net2.*;
import java.util.*;
import org.jcsp.net2.tcpip.TCPIPNodeAddress;

/**
 * @author Quickstone Technologies Limited
 */
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
