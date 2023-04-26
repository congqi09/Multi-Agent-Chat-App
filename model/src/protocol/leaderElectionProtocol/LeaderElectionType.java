package protocol.leaderElectionProtocol;

public enum LeaderElectionType {

    // server -> client
    // server requests a node to start the leader election process if the leader is gone or crashed
    // carry type + subtype
    SERVER_REQUEST,

    // server -> client
    // server authorizes a node to be the leader if the node is the first node joined the p2p network
    // carry type + subtype + leaderToken
    SERVER_AUTH,

    // client -> client
    // the current "leader" node requests to start the leader election process
    // carry type + subtype
    LEADER_REQUEST,

    // client -> client
    // nodes report their status to the node that starts the election
    // carry type + subtype + nodeInfo + performanceWeight
    NODE_REPORT,

    // client -> server
    // the leader node leaves the p2p group and returns the leader token
    // carry type + subtype + nodeInfo + leaderToken
    TOKEN_RETURN,

    // client -> server
    // the node report the result of the leader election
    // carry type + subtype + nodeInfo
    CLIENT_REPORT,

    // client leader -> client
    // leader announces the node is leader
    // carry type + subtype + nodeInfo
    LEADER_CHOSEN

}
