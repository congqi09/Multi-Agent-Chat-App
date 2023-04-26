package com.neu.chatApp.client.data;

import dispatcher.ClientDispatcher;
import io.netty.channel.Channel;
import liveNodeList.ClientLiveNodeList;
import liveNodeList.LiveNodeList;
import node.Node;
import node.NodeChannel;
import p2pConnectionGroup.P2PConnectionGroup;

public class ClientData {
    public static Channel server;
    public static String serverHostname;
    public static int serverHttpPort;
    public static int serverNettyPort;
    public static P2PConnectionGroup group;

    public static String baseURL;

    public static LiveNodeList<NodeChannel> liveNodeList;
    public static Node myNode;

    public static String myHostname;

    public static int myPort;

    public static void init(String hostname, int port) {
        myHostname = hostname;
        myPort = port;
        liveNodeList = new ClientLiveNodeList<>();
        group = new P2PConnectionGroup(port, new ClientDispatcher());
    }


}
