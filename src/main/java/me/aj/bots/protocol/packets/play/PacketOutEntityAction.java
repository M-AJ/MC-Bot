package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.util.Codec;

public class PacketOutEntityAction extends PacketOut {

    private int actionID, jumpBoost;

    public PacketOutEntityAction(Bot bot, int actionID, int jumpBoost) {
        super(bot);
        this.actionID = actionID;
        this.jumpBoost = jumpBoost;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.OUT;
    }

    @Override
    public ByteBuf encode() {
        Codec.writeVarInt32(byteBuf, Bot.get().getEntityId());
        Codec.writeVarInt32(byteBuf, actionID);
        Codec.writeVarInt32(byteBuf, jumpBoost);
        return byteBuf;
    }

    public int getId() {
        return 0x0B;
    }

    public State getState() {
        return State.PLAY;
    }
}
