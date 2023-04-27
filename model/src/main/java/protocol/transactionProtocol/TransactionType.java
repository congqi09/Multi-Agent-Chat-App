package protocol.transactionProtocol;

public enum TransactionType {

    // the leader node send prepare message to all nodes to start a transaction
    // carry type + join/leave type + subType + nodeInfo (the node in join or leave event)
    PREPARE,

    // node uses to reply is able to accept the new node
    // carry type + join/leave type + subType
    ACCEPT,

    // node uses to reply is not able to accept the new node
    // carry type + join/leave type + subType
    ABORT,

    // if no abort message appeared, then the leader node sends commit message to commit
    // carry type + join/leave type + subType + nodeInfo (the node in join or leave event)
    COMMIT,

    // if an abort message presented, then the leader node sends drop message to drop
    // carry type + join/leave type + subType + nodeInfo
    DROP,

    // node uses to reply the commit is done
    // carry type + join/leave type + subType
    ACK_COMMIT,

    // node uses to reply the drop is done
    // carry type + join/leave type + subType
    ACK_DROP


}
