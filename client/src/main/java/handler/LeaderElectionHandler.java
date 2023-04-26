package handler;

import handlerAPI.GeneralEventHandlerAPI;
import io.netty.channel.ChannelHandlerContext;
import protocol.joinAndLeaveProtocol.JoinAndLeaveProtocol;
import protocol.leaderElectionProtocol.LeaderElectionProtocol;

public class LeaderElectionHandler implements GeneralEventHandlerAPI<LeaderElectionProtocol> {

  @Override
  public void handle(LeaderElectionProtocol protocol, ChannelHandlerContext ctx) {

  }
}