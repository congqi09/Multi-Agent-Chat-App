package handler;

import com.neu.chatApp.client.data.ClientData;

import node.NodeChannel;
import protocol.TransmitProtocol;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class CommunicationAPIImpl implements CommunicationAPI {

  public CommunicationAPIImpl() {
  }

  @Override
  public void send(Long id, TransmitProtocol msg) {
    NodeChannel nodeChannel = ClientData.liveNodeList.get(id);
    log.info("Sent message to id: " + id + ", message: " + msg);
    System.out.println(nodeChannel.getChannel());
    nodeChannel.getChannel().writeAndFlush(msg);
  }

  @Override
  public void broadcast(TransmitProtocol msg) {
    Iterator<NodeChannel> allNodes = ClientData.liveNodeList.getAllNodes();
    while (allNodes.hasNext()) {
      NodeChannel next = allNodes.next();
      send(next.getUserId(), msg);
    }
  }
}

