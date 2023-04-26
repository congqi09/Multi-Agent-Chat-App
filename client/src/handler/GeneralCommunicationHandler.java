package handler;

import data.ClientData;
import io.netty.channel.ChannelHandlerContext;
import liveNodeList.LiveNodeList;
import protocol.GeneralType;
import protocol.generalCommunication.GeneralCommunicationProtocol;

@Slf4j
public class GeneralCommunicationHandler implements GeneralEventHandlerAPI<GeneralCommunicationProtocol> {


    public GeneralCommunicationHandler() {}

    @Override
    public void handle(GeneralCommunicationProtocol protocol, ChannelHandlerContext ctx) {
        switch (protocol.getSubType()) {
            case PRIVATE_MESSAGE:
                // look up the sender name
                String senderName = SharableResource.liveNodeList.get(protocol.getSender()).getNickname();
                String message = FormattedPrinter.formatter(true, protocol.getSender(), senderName, protocol.getMessageContent());
                FormattedPrinter.printSystemMessage(message);
                break;
            case BROADCAST_MESSAGE:
                FormattedPrinter.printSystemMessage(FormattedPrinter.formatter(true, protocol.getMessageContent()));
                break;
        }

    }


}
