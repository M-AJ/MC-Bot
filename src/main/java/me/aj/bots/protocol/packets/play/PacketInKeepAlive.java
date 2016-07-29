package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.Bot;
import me.aj.bots.util.Codec;

public class PacketInKeepAlive extends PacketIn {

    private int keepalive;

    public PacketInKeepAlive(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        ByteBuf readableInt = byteBuf.readBytes(byteBuf.readableBytes());
        this.keepalive = Codec.readVarInt32(readableInt);
    }

    @Override
    public void handle() {
        Bot.get().getProtocolManager().sendPacket(new PacketOutKeepAlive(getBot(), keepalive));
    }

    public int getId() {
        return 0x00;
    }

    public State getState() {
        return State.PLAY;
    }
}
