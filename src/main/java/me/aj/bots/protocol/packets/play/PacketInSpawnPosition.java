package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.Bot;
import me.aj.bots.bot.Location;
import me.aj.bots.util.Codec;

public class PacketInSpawnPosition extends PacketIn {

    private Location location;

    public PacketInSpawnPosition(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.location = Codec.readPosition(byteBuf);
    }

    @Override
    public void handle() {
        Bot.get().setLocation(location);
    }

    public int getId() {
        return 0x05;
    }

    public State getState() {
        return State.PLAY;
    }
}
