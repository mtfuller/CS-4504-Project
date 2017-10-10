package edu.kennesaw.cs4504.services;

import edu.kennesaw.cs4504.views.SetupDialog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Thomas on 9/30/2017.
 */
public abstract class TCPPeer {
    private String hostPort;
    private String routerHostIP;
    private int routerPort;
    private SetupDialog peerSetup;
    private Socket tcpSocket;

    public TCPPeer(String name, String routerHostIP, int routerRort) {
        try {
            this.tcpSocket = new Socket(routerHostIP, routerRort);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerHostIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerHostIP);
            e.printStackTrace();
            System.exit(1);
        }
        this.routerHostIP = routerHostIP;
        this.routerPort = routerRort;
        this.peerSetup = new SetupDialog(name, getHostIP()+":"+getHostPort());
    }

    public String getHostIP() {
        return this.tcpSocket.getLocalAddress().toString().substring(1);
    }

    public int getHostPort() {return this.tcpSocket.getLocalPort();}

    public String getRouterHostIP() {
        return routerHostIP;
    }

    public int getRouterPort() {
        return routerPort;
    }

    public Socket getTcpSocket() {
        return tcpSocket;
    }

    public String promptDestIP() {
        String addr = this.peerSetup.showAndWait().get();
        return addr;
    }

    public abstract void run() throws IOException;
}
