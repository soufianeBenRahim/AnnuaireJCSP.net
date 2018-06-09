package com.xpertsoft.annuaireminesante;

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


import Networking.NetworkingAnnuaire;
import Metier.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.filechooser.FileFilter;

import org.jcsp.lang.*; 

  import org.jcsp.net2.*; 
import org.jcsp.net2.bns.BNS;
  import org.jcsp.net2.cns.*; 
  import org.jcsp.net2.tcpip.*; 

/**
 * @author Quickstone Technologies Limited
 */

public class Server {
    static private Bdd gestionBdd = new Bdd();
    static private JFileChooser bddChooser = new JFileChooser(".");
    static  private FileFilter datFilter = null;
    private static final String connectChannel = ".client2serverconnect";
    
  public static void main(String[] args) throws java.io.IOException, Exception {
    MetierAnnuair Metier;
    try{        
       bddChooser.addChoosableFileFilter(datFilter);
       int status = bddChooser.showDialog(null, "Sélection du fichier de configuration de la base");
       if (status == JFileChooser.APPROVE_OPTION) {
       File file = bddChooser.getSelectedFile();
       gestionBdd.deconnexion();
       gestionBdd.initialiserConnexion(file.getAbsolutePath());
       
       }else{
        System.out.println("fin de programme il faut sellectionner un fichier de connfiguration SVP !");
        System.exit(-1);
       }
    } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    } 
  //  ServerSetupDialog ssd = new ServerSetupDialog();

    //dialog is modal so this doesn't execute until it is closed
    //String chatChanName = ssd.getChannelName();
    
    String chatChanName ="JCSPChatChannel";
/*
    NodeKey key;
  
    if (!(Node.getInstance().isInitialized())) {
      try {
        key =
          Node.getInstance().init(
            new TCPIPNodeFactory(InetAddress.getLocalHost().getHostAddress()));
      }
      catch (NodeInitFailedException e) {
        try {
          key =
            Node.getInstance().init(
              new TCPIPAddressID(
                InetAddress.getLocalHost().getHostAddress(),
                TCPIPCNSServer.DEFAULT_CNS_PORT,
                true));
          CNS.install(key);
          NodeAddressID cnsAddress =
            Node.getInstance().getNodeID().getAddresses()[0];
          CNSService.install(key, cnsAddress);
        }
        catch (NodeInitFailedException e2) {
          Node.err.log("Node failed to initialize.");
          System.exit(-1);
        }
      }
    }

*/
/*
new Runnable() {
        @Override
        public void run() {
            try {
                TCPIPNodeServer.main(new String[]{""});
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
   } 
}.run(); 

*/

  String connectChannelName = chatChanName + connectChannel;
TCPIPNodeAddress addr = new TCPIPNodeAddress(4000);
// Initialise Node
Node.getInstance().init(addr);
// Get IP of NodeServer


   
    JFrame serverFrame = new JFrame();
    String s =
      "Chat channel \""
        + chatChanName
        + "\" running on "
        + Node.getInstance().getNodeID();
    JButton exitButton = new JButton("Exit");
    Box vbox = Box.createVerticalBox();
    serverFrame.getContentPane().add(vbox);

    JLabel label = new JLabel(s);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setAlignmentX(0.5F);
    exitButton.setAlignmentX(0.5F);

    vbox.add(label);
    vbox.add(Box.createVerticalStrut(10));
    vbox.add(exitButton);
    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    serverFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    serverFrame.pack();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension td = serverFrame.getSize();
    serverFrame.setBounds(
      (d.width - td.width) / 2,
      (d.height - td.height) / 2,
      td.width,
      td.height);
       InetAddress[] Adress = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
     String[] AdressStr=new String[Adress.length];
     for(int i=0;i<Adress.length;i++){
     AdressStr[i]=Adress[i].getHostAddress();
     }
 
       JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
    String IP = (String)jop.showInputDialog(null, 
      "Veuillez indiquer L'adress IP de l'ecoute !",
      "IP info !",
      JOptionPane.QUESTION_MESSAGE,
      null,
      AdressStr,
      AdressStr[0]);
   
    TCPIPNodeAddress nsAddr = new TCPIPNodeAddress(IP, 7890);
// Initialise CNS and BNS
CNS.initialise(nsAddr);
BNS.initialise(nsAddr);
    serverFrame.setVisible(true);
   
  

NetChannelInput in = CNS.net2one(connectChannelName);
    Metier=new MetierAnnuair(gestionBdd);
        CSProcess[] processes = { new NetworkingAnnuaire(in,Metier) };
        new Parallel(processes).run();

  }
}
