package me.aj.bots.protocol.packets.login;

import io.netty.buffer.ByteBuf;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.Bot;
import me.aj.bots.util.Codec;

public class PacketInSetCompression extends PacketIn {

    private int threshold;

    public PacketInSetCompression(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.threshold = Codec.readVarInt32(byteBuf);
    }

    @Override
    public void handle() {
        if (threshold != -1) {
            Bot.get().getClientConnection().setCompressionEnabled(true);
        }
        Bot.get().getClientConnection().setCompressionThreshold(threshold);
        System.out.print("\nReceived Set Compression, threshold: " + threshold);
    }

    public int getId() {
        return 0x03;
    }

    public State getState() {
        return State.LOGIN;
    }
}
