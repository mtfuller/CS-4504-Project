package edu.kennesaw.cs4504.services;

import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerRouter {

    public static void main(String[] args) throws IOException {
        Socket clientSocket = null; // socket for the thread
        Object[][] RoutingTable = new Object[10][2]; // routing table
        int SockNum = 5555; // port number
        Boolean Running = true;
        int ind = 0; // indext in the routing table

        //Accepting connections
        ServerSocket serverSocket = null; // server socket for accepting connections
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("ServerRouter IP: "+getIPv4InetAddress());
            System.out.println("ServerRouter is Listening on port: 5555.");
        } catch (MalformedURLException ex) {
            System.out.println("Could not get public IP.");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5555.");
            System.exit(1);
        }

        // Creating threads with accepted connections
        while (Running) {
            try {
                clientSocket = serverSocket.accept();
                SThread t = new SThread(RoutingTable, clientSocket, ind); // creates a thread with a random port
                t.start(); // starts the thread
                ind++; // increments the index
                System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.err.println("Client/Server failed to connect.");
                System.exit(1);
            }
        }//end while

        //closing connections
        clientSocket.close();
        serverSocket.close();
    }

    public static String getIPv4InetAddress() throws SocketException, UnknownHostException {
      String ip = null;
      try {
          Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
          while (interfaces.hasMoreElements()) {
              NetworkInterface iface = interfaces.nextElement();
              // filters out 127.0.0.1 and inactive interfaces
              if (iface.isLoopback() || !iface.isUp())
                  continue;

              Enumeration<InetAddress> addresses = iface.getInetAddresses();
              while(addresses.hasMoreElements()) {
                  InetAddress addr = addresses.nextElement();

                  // *EDIT*
                  if (addr instanceof Inet6Address) continue;

                  ip = addr.getHostAddress();
                  System.out.println("Found Network Interface: " + iface.getDisplayName() + " " + ip);
              }
          }
      } catch (SocketException e) {
          throw new RuntimeException(e);
      }
      return ip;
  }

    public static String getPublicIP() throws IOException, MalformedURLException {
      URL url = new URL("http://checkip.amazonaws.com/");
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
      return br.readLine();
    }
}
