package handler;

import data.ClientData;
import io.netty.channel.ChannelHandlerContext;
import liveNodeList.LiveNodeList;
import protocol.GeneralType;
import protocol.generalCommunication.GeneralCommunicationProtocol;

public class GeneralCommunicationHandler implements GeneralEventHandlerAPI<GeneralCommunicationProtocol> {

    public void handle(GeneralCommunicationProtocol protocol, ChannelHandlerContext handlerContext) {
        switch (protocol.getGeneralCommunicationType()) {
            case PRIVATE_MESSAGE:
                // look up the sender name
                String senderName =  ClientData.liveNodeList.get(protocol.getSender()).getUserName();
                //String message = FormattedPrinter.formatter(true, protocol.getSender(), senderName, protocol.getMessageContent());
                //FormattedPrinter.printSystemMessage(message);
                break;
            case BROADCAST_MESSAGE:
                //FormattedPrinter.printSystemMessage(FormattedPrinter.formatter(true, protocol.getMessageContent()));
                break;
        }
    }
}
