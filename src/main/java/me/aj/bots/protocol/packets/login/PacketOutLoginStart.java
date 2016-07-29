package me.aj.bots.protocol.packets.login;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.util.Codec;

import java.io.IOException;

public class PacketOutLoginStart extends PacketOut {

    public PacketOutLoginStart(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() throws IOException {
        Codec.writeString(byteBuf, Bot.get().getClientConnection().getName());
        return byteBuf;
    }

    public int getId() {
        return 0x00;
    }

    public State getState() {
        return State.LOGIN;
    }
}
