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
import org.jcsp.net.*;
import java.util.*;
import org.jcsp.plugNplay.ProcessWrite;

/**
 * @author Quickstone Technologies Limited
 */
public class ConnectionAuthenticator implements CSProcess {

    NetAltingChannelInput connectIn;
    NetAltingChannelInput getLocationIn;
    Hashtable hash;
    MetierAnnuair m;

    public ConnectionAuthenticator(NetAltingChannelInput conect,NetAltingChannelInput getLocaton, MetierAnnuair _m) {
        connectIn = conect;

        getLocationIn = getLocaton;
        hash = new Hashtable();
        m = _m;
    }

    public void run() {

        AltingChannelInput[] chans = {connectIn,  getLocationIn};
        Alternative alt = new Alternative(chans);
        while (true) {
            switch (alt.priSelect()) {
                case 0:
                    Object o = connectIn.read();
   
                    if (o instanceof Contact) {
                         Contact c = (Contact) o;
                        if(c.isConnect()){
                            if (m.login(c.getPSUDO(), c.getPASSWORS())) {
                            addOutputChannel(c);
                        }
                        }else{
                            if (m.desconnect(c.getPSUDO(), c.getPASSWORS())) {
                            removeOutputChannel(c);
                        }
                        }
                    }
                    break;
                case 1:
                    Object o2 = getLocationIn.read();
                    if (o2 instanceof Contact) {
                        Contact c2 = (Contact) o2;
                        c2.getReturnChan().write(hash.get((c2.getPSUDO())));
                    }
                    break;
            }
        }
    }

    private void addOutputChannel(Contact cb) {
        ProcessWrite p = new ProcessWrite(cb.getReturnChan());
        hash.put(cb.getPSUDO(), p);
    }

    private void removeOutputChannel(Contact cb) {
        System.out.println("removing outputchan " + cb.getPSUDO());
        ProcessWrite p = (ProcessWrite) hash.get(cb.getPSUDO());
        hash.remove(cb.getPSUDO());
        System.out.println("removed");
    }
}
