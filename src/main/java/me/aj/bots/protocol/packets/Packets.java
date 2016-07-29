package me.aj.bots.protocol.packets;

import com.google.common.collect.Maps;
import me.aj.bots.netty.TransitPacket;
import me.aj.bots.protocol.packets.play.*;
import me.aj.bots.Bot;
import me.aj.bots.protocol.packets.login.PacketInLoginSuccess;
import me.aj.bots.protocol.packets.login.PacketInSetCompression;
import me.aj.bots.protocol.packets.status.PacketInResponse;

import java.util.HashMap;
import java.util.Map;

public class Packets {

    private static final HashMap<Integer, Class<? extends Packet>> handshakePackets = Maps.newHashMap();
    private static final HashMap<Integer, Class<? extends Packet>> statusPackets = Maps.newHashMap();
    private static final HashMap<Integer, Class<? extends Packet>> loginPackets = Maps.newHashMap();
    private static final HashMap<Integer, Class<? extends Packet>> playPackets = Maps.newHashMap();

    public Packets() {
        statusPackets.put(0x00, PacketInResponse.class);
        loginPackets.put(0x02, PacketInLoginSuccess.class);
        loginPackets.put(0x03, PacketInSetCompression.class);
        playPackets.put(0x00, PacketInKeepAlive.class);
        playPackets.put(0x01, PacketInJoinGame.class);
        playPackets.put(0x02, PacketInChatMessage.class);
        playPackets.put(0x05, PacketInSpawnPosition.class);
        playPackets.put(0x08, PacketInPlayerPositionLook.class);
    }

    public void handlePacket(TransitPacket transitPacket) {
        int id = transitPacket.getId();
        Map<Integer, Class<? extends Packet>> suite = null;
        switch (Bot.get().getClientConnection().getState()) {
            case STATUS:
                suite = statusPackets;
                break;
            case LOGIN:
                suite = loginPackets;
                break;
            case HANDSHAKE:
                suite = handshakePackets;
                break;
            case PLAY:
                suite = playPackets;
                break;
        }
        Class<? extends Packet> PACKET_CLASS = suite.get(id);
        try {
            if (PACKET_CLASS != null) {
                Packet packet = PACKET_CLASS.getConstructor(Bot.class).newInstance(Bot.get());
                //This is used to only handle incoming packets
                PacketIn in = (PacketIn) packet;
                in.decode(transitPacket.getPacketData());
                in.handle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
