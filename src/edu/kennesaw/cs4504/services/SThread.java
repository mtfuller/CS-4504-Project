package edu.kennesaw.cs4504.services;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class SThread extends Thread {
    private Object[][] RTable; // routing table
    private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
    private BufferedReader in; // reader (for reading from the machine connected to)
    private String inputLine, outputLine, destination, addr; // communication strings
    private Socket outSocket; // socket for communicating with a destination
    private int ind; // indext in the routing table

    // Constructor
    SThread(Object[][] Table, Socket toClient, int index) throws IOException {
        out = new PrintWriter(toClient.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
        RTable = Table;
        addr = toClient.getInetAddress().getHostAddress();
        RTable[index][0] = addr+":"+Integer.toString(toClient.getPort()); // IP addresses
        RTable[index][1] = toClient; // sockets for communication
        ind = index;
        for (Object[] row : RTable) {
            if (row[0] == null) continue;
            String a = (String) row[0];
            Socket s = (Socket) row[1];
            String ip = s.getInetAddress().getHostAddress();
            String port = Integer.toString(s.getPort());
            System.out.println(String.format("%10s : %10s %10s", a, ip, port));
        }
    }

    // Run method (will run for each machine that connects to the ServerRouter)
    public void run() {
        try {
            // Initial sends/receives
            destination = in.readLine(); // initial read (the destination for writing)
            System.out.println("Forwarding to " + destination);
            out.println("Connected to the router."); // confirmation of connection

            // waits 10 seconds to let the routing table fill with all machines' information
            try {
                System.out.println("WAITING...");
                this.sleep(10000);
                System.out.println("FINISHED!");
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted");
            }

            // loops through the routing table to find the destination
            for (int i = 0; i < 10; i++) {
                if (destination.equals(RTable[i][0])) {
                    outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
                    System.out.println("Found destination: " + destination);
                    outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
                }
            }

            // Communication loop
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client/Server said: " + inputLine);
                if (inputLine.equals("Bye.")) // exit statement
                    break;
                outputLine = inputLine; // passes the input from the machine to the output string for the destination

                if (outSocket != null) {
                    outTo.println(outputLine); // writes to the destination
                }
            }// end while
        }// end try
        catch (IOException e) {
            System.err.println("Could not listen to socket.");
            System.exit(1);
        }
    }
}