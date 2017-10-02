package edu.kennesaw.cs4504.services;

import java.io.*;
import java.net.*;

public class TCPServer extends TCPPeer {
    public TCPServer(String routerHostIP, int routerRort) throws UnknownHostException {
        super("Server", routerHostIP, routerRort);
    }

    @Override
    public void run() throws IOException {
        System.out.println("SERVER:");

        // Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        String host = getHostIP(); // Server machine's IP
        String routerName = getRouterHostIP();//"j263-08.cse1.spsu.edu"; // ServerRouter host name
        int SockNum = getRouterPort(); // port number

        out = new PrintWriter(getTcpSocket().getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(getTcpSocket().getInputStream()));

        // Variables for message passing
        String fromServer; // messages sent to ServerRouter
        String fromClient; // messages received from ServerRouter
        String address = promptDestIP(); // destination IP (Client)

        // Communication process (initial sends/receives)
        out.println(address);// initial send (IP of the destination Client)
        fromClient = in.readLine();// initial receive from router (verification of connection)
        System.out.println("ServerRouter: " + fromClient);

        // Communication while loop
        while ((fromClient = in.readLine()) != null) {
            System.out.println("Client said: " + fromClient);
            if (fromClient.equals("Bye.")) // exit statement
                break;
            fromServer = fromClient.toUpperCase(); // converting received message to upper case
            System.out.println("Server said: " + fromServer);
            out.println(fromServer); // sending the converted message back to the Client via ServerRouter
        }

        // closing connections
        out.close();
        in.close();
        getTcpSocket().close();
    }
}
