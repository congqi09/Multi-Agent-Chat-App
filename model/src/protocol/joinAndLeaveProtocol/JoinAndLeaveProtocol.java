package protocol.joinAndLeaveProtocol;

import node.Node;
import protocol.GeneralType;
import protocol.TransmitProtocol;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JoinAndLeaveProtocol extends TransmitProtocol {

    private JoinAndLeaveType subType;

    private Node nodeInfo;

    public JoinAndLeaveProtocol(GeneralType type, JoinAndLeaveType subType) {
        super(type);
        this.subType = subType;
    }

    public JoinAndLeaveProtocol(GeneralType type, JoinAndLeaveType subType, Node nodeInfo) {
        super(type);
        this.subType = subType;
        this.nodeInfo = nodeInfo;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
