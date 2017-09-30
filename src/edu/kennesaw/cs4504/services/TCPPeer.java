package edu.kennesaw.cs4504.services;

import edu.kennesaw.cs4504.views.SetupDialog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Thomas on 9/30/2017.
 */
public abstract class TCPPeer {
    private String hostIP;
    private String hostPort;
    private String routerHostIP;
    private int routerPort;
    private SetupDialog peerSetup;

    public TCPPeer(String name, String routerHostIP, int routerRort) throws UnknownHostException {
        this.hostIP = InetAddress.getLocalHost().getHostAddress();
        this.routerHostIP = routerHostIP;
        this.routerPort = routerRort;
        this.peerSetup = new SetupDialog(name, this.hostIP);
    }

    public String getHostIP() {
        return hostIP;
    }

    public String getRouterHostIP() {
        return routerHostIP;
    }

    public int getRouterPort() {
        return routerPort;
    }

    public String promptDestIP() {
        String addr = this.peerSetup.showAndWait().get();
        return addr;
    }

    public abstract void run() throws IOException;
}
