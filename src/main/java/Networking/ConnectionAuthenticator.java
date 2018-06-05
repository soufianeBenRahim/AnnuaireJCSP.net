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
public class ConnectionAuthenticator implements CSProcess {

    NetChannelInput chanelIn;

    Hashtable hash;
    MetierAnnuair m;

    public ConnectionAuthenticator(NetChannelInput in, MetierAnnuair _m) {
        chanelIn = in;
        hash = new Hashtable();
        m = _m;
    }

    public void run() {
        while (true) {

            Object o = chanelIn.read();

            if (o instanceof Contact) {
                Contact c = (Contact) o;
                switch (c.getTypeRequist()) {
                    case Contact.GETLOCATION:
                        TCPIPNodeAddress chan = (TCPIPNodeAddress) hash.get(c.getPSUDO());
                        System.out.println("GetLocation recu de psudo :"+c.getPSUDO());
                      //  chan.(chan);
                        break;
                    case Contact.CONNECT:
                        NodeID remoteID =
                        LinkFactory.getLink(c.getNodAdress()).getRemoteNodeID();
                        NetChannelOutput out = NetChannel.one2net(remoteID, 46);
                        if (m.login(c.getPSUDO(), c.getPASSWORS())) {
                            addOutputAdress(c);
                            out.write(true);
                        } else {
                            out.write(false);
                        }
                        break;
                    case Contact.DISCONNECT:
                        if (m.desconnect(c.getPSUDO(), c.getPASSWORS())) {
                            removeOutputAdress(c);
                    //        c.getReturnChan().write(true);
                        } else {
                     //       c.getReturnChan().write(false);
                        }
                        break;
                }
            }

        }
    }

    private void addOutputAdress(Contact cb) {
        System.out.println("add contact :"+cb.getPSUDO());
        hash.put(cb.getPSUDO(), cb.getNodAdress());
    }

    private void removeOutputAdress(Contact cb) {
        System.out.println("removing outputchan " + cb.getPSUDO());
     
        hash.remove(cb.getPSUDO());
        System.out.println("removed");
    }
}
