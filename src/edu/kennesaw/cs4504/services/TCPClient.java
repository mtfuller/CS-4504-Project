package edu.kennesaw.cs4504.services;

import java.io.*;
import java.net.*;

public class TCPClient extends TCPPeer {
    public TCPClient(String routerHostIP, int routerRort) throws UnknownHostException {
        super("Client", routerHostIP, routerRort);
    }

    public void run() throws IOException {
        System.out.println("CLIENT:");

        // Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        String host = getHostIP(); // Client machine's IP

        out = new PrintWriter(getTcpSocket().getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(getTcpSocket().getInputStream()));

        // Variables for message passing
        String path = new File(".").getAbsolutePath();
        Reader reader = new FileReader("res/file.txt");
        BufferedReader fromFile = new BufferedReader(reader); // reader for the string file
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
        String address = promptDestIP(); // destination IP (Server)
        long t0, t1, t;

        // Communication process (initial sends/receives
        out.println(address);// initial send (IP of the destination Server)
        fromServer = in.readLine();//initial receive from router (verification of connection)
        System.out.println("ServerRouter: " + fromServer);
        out.println(host); // Client sends the IP of its machine as initial send
        t0 = System.currentTimeMillis();

        // Communication while loop
        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            t1 = System.currentTimeMillis();
            if (fromServer.equals("Bye.")) // exit statement
                break;
            t = t1 - t0;
            System.out.println("Cycle time: " + t);

            fromUser = fromFile.readLine(); // reading strings from a file
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser); // sending the strings to the Server via ServerRouter
                t0 = System.currentTimeMillis();
            }
        }

        // closing connections
        out.close();
        in.close();
        getTcpSocket().close();
    }
}
