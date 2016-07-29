package me.aj.bots.protocol.packets.handshake;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.util.Codec;

import java.io.IOException;

public class PacketOutHandshake extends PacketOut {

    public PacketOutHandshake(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() throws IOException {
        Codec.writeVarInt32(byteBuf, getBot().getClientConnection().getProtocolVersion()); //protocol version
        Codec.writeString(byteBuf, getBot().getClientConnection().getServerIp());
        byteBuf.writeShort(getBot().getClientConnection().getServerPort()); //port
        Codec.writeVarInt32(byteBuf, Bot.get().getClientConnection().getHandshakeStatus()); //state (1 for handshake)
        return byteBuf;
    }

    public int getId() {
        return 0x00;
    }

    public State getState() {
        return State.HANDSHAKE;
    }
}
