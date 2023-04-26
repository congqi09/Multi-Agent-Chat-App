package protocol.generalCommunication;

import lombok.Data;
import lombok.NoArgsConstructor;
import protocol.GeneralType;
import protocol.TransmitProtocol;

@Data
@NoArgsConstructor
public class GeneralCommunicationProtocol extends TransmitProtocol {
    private GeneralCommunicationType generalCommunicationType;
    private Long sender;
    private Long receiver;
    private String messageContent;

    public GeneralCommunicationProtocol(GeneralType generalType, GeneralCommunicationType generalCommunicationType, Long sender, String messageContent) {
        super(generalType);
        this.generalCommunicationType = generalCommunicationType;
        this.sender = sender;
        this.messageContent = messageContent;
    }

    public GeneralCommunicationProtocol(GeneralType generalType, GeneralCommunicationType generalCommunicationType, Long sender, Long receiver, String messageContent) {
        super(generalType);
        this.generalCommunicationType = generalCommunicationType;
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
