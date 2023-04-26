package handler;

import handler.joinAndLeave.JoinAndLeaveHandler;
import data;
import driver.UI;
import com.neu.chatApp.util.handlerAPI;
import node.NodeChannel;
import protocol.GeneralType;
import protocol.JoinAndLeaveProtocol;
import protocol.JoinAndLeaveType;
import protocol.TransactionProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TransactionHandler implements GeneralEventHandlerAPI<TransactionProtocol> {

  private final TransactionAPI transactionAPI;

  public static TransactionProtocol currentNodeInTransaction;

  private int countAccept = 0;
  private int countAbort = 0;
  private int countACKCommit = 0;
  private int countACKDrop = 0;

  private boolean isPhase1Completed;

  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

  public TransactionHandler() {
    this.transactionAPI = new TransactionImpl();
    this.phase1Analyzer();
    this.phase2Analyzer();
  }

  @Override
  public void handle(TransactionProtocol protocol, ChannelHandlerContext ctx) {
    switch (protocol.getSubType()) {
      case PREPARE:
        switch (protocol.getMainType()) {
          case JOIN:
            boolean isContain = SharableResource.liveNodeList.isContain(protocol.getNodeInfo().getId());
            if (isContain) {
              transactionAPI.abort(ctx.channel());
            } else {
              transactionAPI.accept(ctx.channel());
            }
            break;
          case LEAVE:
            boolean isExisted = SharableResource.liveNodeList.isContain(protocol.getNodeInfo().getId());
            if (isExisted) {
              transactionAPI.accept(ctx.channel());
            } else {
              transactionAPI.abort(ctx.channel());
            }
            break;
        }
        break;
      case ACCEPT:
        if (SharableResource.myNode.isLeader()) {
          log.info("Received ACCEPT response");
          countAccept++;
        }
        break;
      case ABORT:
        if (SharableResource.myNode.isLeader()) {
          log.info("Received ABORT response");
          countAbort++;
        }
        break;
      case COMMIT:
        log.info("Committing the transaction");
        if (JoinAndLeaveType.JOIN.equals(protocol.getMainType())) {
          connectTo(protocol);
          // send ack
          transactionAPI.ackCommit(ctx.channel());
        } else if (JoinAndLeaveType.LEAVE.equals(protocol.getMainType())) {
          // send ack
          transactionAPI.ackCommit(ctx.channel());
          SharableResource.liveNodeList.remove(protocol.getNodeInfo().getId());
          log.info("Broke connection with the node: " + protocol.getNodeInfo());
        }
        break;
      case DROP:
        log.info("Drop the transaction");
        // send ack
        transactionAPI.ackDrop(ctx.channel());
        // do nothing
        break;
      case ACK_COMMIT:
        if (SharableResource.myNode.isLeader()) {
          log.info("Received ack commit");
          countACKCommit++;
        }
        break;
      case ACK_DROP:
        if (SharableResource.myNode.isLeader()) {
          log.info("Received ack drop");
          countACKDrop++;
        }
        break;
    }
  }

  private void resetPhase1Counter() {
    countAccept = 0;
    countAbort = 0;
  }

  private void resetPhase2Counter() {
    countACKCommit = 0;
    countACKDrop = 0;
    currentNodeInTransaction = null;
    isPhase1Completed = false;
    // tell the handler the transaction has done
    JoinAndLeaveHandler.unlock();
  }


  private void phase1Analyzer() {
    executorService.scheduleAtFixedRate(() -> {
      // exclude the current node in transaction and self
      while (currentNodeInTransaction != null && !isPhase1Completed) {
        if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.LEAVE)) {
          // if self exit
          if (SharableResource.myNode.getId().equals(currentNodeInTransaction.getNodeInfo().getId())) {
            if (countAccept + countAbort == SharableResource.liveNodeList.size()) {
              phase1Processor();
            }
          } else {
            if (countAccept + countAbort == SharableResource.liveNodeList.size() - 1) {
              phase1Processor();
            }
          }
        } else if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.JOIN)) {
          if (countAccept + countAbort == SharableResource.liveNodeList.size()) {
            phase1Processor();
          }
        }
      }
    }, 300, 700, TimeUnit.MILLISECONDS);
  }

  private void phase1Processor() {
    log.info("Phase 1 completed");
    if (countAbort > 0) {
      // send drop
      transactionAPI.drop(currentNodeInTransaction.getNodeInfo(), currentNodeInTransaction.getMainType());
      log.info("Abort message occurred in transaction, a DROP message sent");
      resetPhase1Counter();
      isPhase1Completed = true;
      return;
    }
    // check self
    if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.JOIN)) {
      if (SharableResource.liveNodeList.isContain(currentNodeInTransaction.getNodeInfo().getId())) {
        // drop
        transactionAPI.drop(currentNodeInTransaction.getNodeInfo(), currentNodeInTransaction.getMainType());
        log.info("Abort message occurred in transaction, a DROP message sent");
      } else {
        // commit
        transactionAPI.commit(currentNodeInTransaction.getNodeInfo(), currentNodeInTransaction.getMainType());
        log.info("No Abort message occurred in transaction, a COMMIT message sent");
      }
    } else if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.LEAVE)) {
      // if self exit
      if (SharableResource.myNode.getId().equals(currentNodeInTransaction.getNodeInfo().getId())) {
        transactionAPI.commit(currentNodeInTransaction.getNodeInfo(), currentNodeInTransaction.getMainType());
        log.info("No Abort message occurred in transaction, a COMMIT message sent");
      } else {
        if (SharableResource.liveNodeList.isContain(currentNodeInTransaction.getNodeInfo().getId())) {
          // commit
          transactionAPI.commit(currentNodeInTransaction.getNodeInfo(), currentNodeInTransaction.getMainType());
          log.info("No Abort message occurred in transaction, a COMMIT message sent");
        } else {
          // drop
          transactionAPI.drop(currentNodeInTransaction.getNodeInfo(), currentNodeInTransaction.getMainType());
          log.info("Abort message occurred in transaction, a DROP message sent");
        }
      }
    }
    isPhase1Completed = true;
    resetPhase1Counter();
  }



  private void phase2Analyzer() {
    executorService.scheduleAtFixedRate(() -> {
      // exclude the current node in transaction and self
      while (currentNodeInTransaction != null && isPhase1Completed) {
        if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.LEAVE)) {
          // if self exit
          if (SharableResource.myNode.getId().equals(currentNodeInTransaction.getNodeInfo().getId())) {
            if ((countACKCommit == SharableResource.liveNodeList.size() || countACKDrop == SharableResource.liveNodeList.size())) {
              phase2Processor();
            }
          } else {
            if ((countACKCommit == SharableResource.liveNodeList.size() - 1 || countACKDrop == SharableResource.liveNodeList.size() - 1)) {
              phase2Processor();
            }
          }
        } else if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.JOIN)) {
          if ((countACKCommit == SharableResource.liveNodeList.size() || countACKDrop == SharableResource.liveNodeList.size())) {
            phase2Processor();
          }
        }
      }
    }, 300, 700, TimeUnit.MILLISECONDS);
  }

  private void phase2Processor() {
    log.info("Phase 2 completed");
    // do action on self
    if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.JOIN)) {
      connectTo(currentNodeInTransaction);
      // report to server
      SharableResource.server.writeAndFlush(new JoinAndLeaveProtocol(GeneralType.JOIN_AND_LEAVE, JoinAndLeaveType.JOIN, currentNodeInTransaction.getNodeInfo()));
    } else if (currentNodeInTransaction.getMainType().equals(JoinAndLeaveType.LEAVE)) {
      // check if not self
      if (!currentNodeInTransaction.getNodeInfo().getId().equals(SharableResource.myNode.getId())) {
        // send ack to the exit node
        JoinAndLeaveProtocol res = new JoinAndLeaveProtocol(GeneralType.JOIN_AND_LEAVE, JoinAndLeaveType.LEAVE_OK);
        log.info("Sent LEAVE_OK to the exiting node: " + res);
        SharableResource.liveNodeList.get(currentNodeInTransaction.getNodeInfo().getId()).getChannel().writeAndFlush(res);
        log.info("Broke connection with the node: " + currentNodeInTransaction.getNodeInfo());
        SharableResource.liveNodeList.remove(currentNodeInTransaction.getNodeInfo().getId());
        // report to server
        SharableResource.server.writeAndFlush(new JoinAndLeaveProtocol(GeneralType.JOIN_AND_LEAVE, JoinAndLeaveType.LEAVE, currentNodeInTransaction.getNodeInfo()));
      } else {
        resetPhase2Counter();
        UI.isLeft = true;
        return;
      }
    }
    resetPhase2Counter();
  }

  private void connectTo(TransactionProtocol currentNodeInTransaction) {
    try {
      Channel connect = SharableResource.group.connect(currentNodeInTransaction.getNodeInfo().getHostname(), currentNodeInTransaction.getNodeInfo().getPort());
      // add to live node list
      SharableResource.liveNodeList.add(new NodeChannel(currentNodeInTransaction.getNodeInfo(), connect));
      log.info("Established connection with a new node: " + currentNodeInTransaction.getNodeInfo());
      // send greeting message
      JoinAndLeaveProtocol greeting = new JoinAndLeaveProtocol(GeneralType.JOIN_AND_LEAVE, JoinAndLeaveType.GREETING, SharableResource.myNode);
      log.info("Sent GREETING response for JOIN request of node: " + currentNodeInTransaction.getNodeInfo());
      connect.writeAndFlush(greeting);
    } catch (SocketTimeoutException ignored) {
      // the exception shouldn't be happened since the node that requested join and leave should keep connection with the leader node
    }
  }
}
