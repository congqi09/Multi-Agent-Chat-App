package data;

import io.netty.channel.Channel;
import node.NodeChannel;

public class ClientData {
    public static Channel server;
    public static String serverHostname;
    public static int serverHttpPort;
    public static int serverNettyPort;

    public static String baseURL;

    public static LiveNodeList<NodeChannel> liveNodeList;


}
