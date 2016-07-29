package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;

public class PacketOutPlayerPositionLook extends PacketOut {

    private double x, y, z;
    private boolean onground;
    private float yaw, pitch;

    public PacketOutPlayerPositionLook(Bot bot, double x, double y, double z, float yaw, float pitch, boolean onground) {
        super(bot);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onground = onground;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() {
        byteBuf.writeDouble(x);
        byteBuf.writeDouble(y);
        byteBuf.writeDouble(z);
        byteBuf.writeFloat(yaw);
        byteBuf.writeFloat(pitch);
        byteBuf.writeBoolean(onground);
        return byteBuf;
    }

    public int getId() {
        return 0x06;
    }

    public State getState() {
        return State.PLAY;
    }
}
