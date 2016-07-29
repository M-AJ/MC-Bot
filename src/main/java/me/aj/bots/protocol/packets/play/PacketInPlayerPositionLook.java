package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.bot.Location;
import me.aj.bots.protocol.packets.PacketIn;

public class PacketInPlayerPositionLook extends PacketIn {

    private double x, y, z;
    private float yaw, pitch;
    private byte flags;

    private Location location;

    public PacketInPlayerPositionLook(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.x = byteBuf.readDouble();
        this.y = byteBuf.readDouble();
        this.z = byteBuf.readDouble();
        this.yaw = byteBuf.readFloat();
        this.pitch = byteBuf.readFloat();
        this.flags = byteBuf.readByte();
        this.location = new Location(x, y, z, yaw, pitch);
        Bot.get().setLocation(location);
    }

    @Override
    public void handle() {
        Bot.get().getProtocolManager()
                .sendPacket(
                        new PacketOutPlayerPositionLook
                                (Bot.get(), x, y, z, yaw, pitch, true));
    }

    public int getId() {
        return 0x08;
    }

    public State getState() {
        return State.PLAY;
    }
}
