package me.aj.bots.protocol;

import io.netty.channel.Channel;
import lombok.Getter;
import me.aj.bots.protocol.packets.PacketOut;
import me.aj.bots.protocol.packets.Packets;

@Getter
public class ProtocolManager {

    private Packets packets;
    private Channel channel;

    public ProtocolManager(Channel channel) {
        this.packets = new Packets();
        this.channel = channel;
    }

    public void sendPacket(PacketOut packet) {
        channel.writeAndFlush(packet);
    }
}
