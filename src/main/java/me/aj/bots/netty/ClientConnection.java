package me.aj.bots.netty;

import lombok.Getter;
import lombok.Setter;
import me.aj.bots.protocol.packets.Packet;
import me.aj.bots.util.Codec;

@Getter
public class ClientConnection {

    private String name = "BOT_" + Codec.randInt(0, 1000);
    private int protocolVersion = 47;
    private String serverIp = "localhost";
    private int serverPort = 25565;
    private final int handshakeStatus = 2;

    @Setter
    private Packet.State state = Packet.State.HANDSHAKE;
    @Setter
    private boolean compressionEnabled = false;
    @Setter
    private int compressionThreshold = -1;

    public ClientConnection(String name, int protocolVersion, String ip, int port) {
        this.name = name;
        this.protocolVersion = protocolVersion;
        this.serverIp = ip;
        this.serverPort = port;
    }
}
