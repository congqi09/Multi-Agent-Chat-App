package handler;

import data;
import node.NodeChannel;
import protocol.GeneralType;
import protocol.TransmitProtocol;
import protocol.GeneralCommunicationProtocol;
import protocol.GeneralCommunicationType;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class CommunicationAPIImpl implements CommunicationAPI {

  public CommunicationAPIImpl() {}

  @Override
  public void send(Long id, TransmitProtocol msg) {
    NodeChannel nodeChannel = SharableResource.liveNodeList.get(id);
    log.info("Sent message to id: " + id + ", message: " + msg);
    System.out.println(nodeChannel.getChannel());
    nodeChannel.getChannel().writeAndFlush(msg);
  }

  @Override
  public void broadcast(TransmitProtocol msg) {
    Iterator<NodeChannel> allNodes = SharableResource.liveNodeList.getAllNodes();
    while (allNodes.hasNext()) {
      NodeChannel next = allNodes.next();
      send(next.getId(), msg);
    }
  }

