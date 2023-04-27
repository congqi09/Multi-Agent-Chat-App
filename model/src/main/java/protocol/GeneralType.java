package protocol;

/**
 * General type for each module handler to identify its task.
 */
public enum GeneralType {

    // the top type of leader election
    LEADER_ELECTION,

    // the top type of general message communication
    GENERAL_COMMUNICATION,

    // the top type of join and leave
    JOIN_AND_LEAVE,

    // the top type of transaction
    TRANSACTION

}
