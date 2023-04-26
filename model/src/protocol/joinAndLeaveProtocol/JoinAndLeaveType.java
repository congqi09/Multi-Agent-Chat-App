package protocol.joinAndLeaveProtocol;

public enum JoinAndLeaveType {

    // An external node intends to join the network
    // the leader node use the node info to start a transaction
    // carry type + subType + nodeInfo
    JOIN,

    // Use for node to send it information to the node requested join and leave
    // carry type + subType + nodeInfo
    GREETING,

    // A node in the network intends to leave the network
    // the leader node use the node info to start a transaction
    // carry type + subType + nodeInfo
    LEAVE,

    // use for leader node tells the exiting node that the transaction is done
    // it can exit safely
    // carry type + subType
    LEAVE_OK
}
