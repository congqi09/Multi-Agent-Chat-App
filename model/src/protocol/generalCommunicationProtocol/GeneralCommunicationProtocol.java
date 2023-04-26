package protocol.generalCommunicationProtocol;

import protocol.GeneralType;
import protocol.TransmitProtocol;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class GeneralCommunicationProtocol extends TransmitProtocol {

    private GeneralCommunicationType subType;

    private Long sender;

    private Long receiver;
    private String messageContent;

    public GeneralCommunicationProtocol(GeneralType type, GeneralCommunicationType subType, Long sender, String messageContent) {
        super(type);
        this.subType = subType;
        this.sender = sender;
        this.messageContent = messageContent;
    }

    public GeneralCommunicationProtocol(GeneralType type, GeneralCommunicationType subType, Long sender, Long receiver, String messageContent) {
        super(type);
        this.subType = subType;
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
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
