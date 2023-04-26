package protocol;

public enum GeneralType {
    LEADER_ELECTION,

    // the top type of general message communication
    GENERAL_COMMUNICATION,

    // the top type of join and leave
    JOIN_AND_LEAVE,

    // the top type of transaction
    TRANSACTION
}
