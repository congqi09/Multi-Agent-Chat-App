package data;

import io.netty.channel.Channel;
import liveNodeList.ClientLiveNodeList;
import liveNodeList.LiveNodeList;
import node.Node;
import node.NodeChannel;

public class ClientData {
    public static Channel server;
    public static String serverHostname;
    public static int serverHttpPort;
    public static int serverNettyPort;

    public static String baseURL;

    public static LiveNodeList<NodeChannel> liveNodeList;

    // TODO: p2p connection group need to be built
    public static Node myNode;

    public static String myHostname;

    public static int myPort;

    public static void init(String hostname, int port) {
        myHostname = hostname;
        myPort = port;
        liveNodeList = new ClientLiveNodeList<>();
        // group = new P2PConnectionGroup(port, new ClientTaskDispatcher());
    }


}
