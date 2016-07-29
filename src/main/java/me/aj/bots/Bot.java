package me.aj.bots;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.aj.bots.bot.*;
import me.aj.bots.netty.*;
import me.aj.bots.protocol.ProtocolManager;

@Getter
@Setter
public class Bot {

    @Getter(AccessLevel.NONE)
    private static Bot instance;
    private ProtocolManager protocolManager;
    @Setter(AccessLevel.NONE)
    private ClientConnection clientConnection;

    //Player Things - Initialized from PacketIn;s
    private int entityId;
    private GameMode gameMode;
    private Dimension dimension;
    private Difficulty difficulty;
    private short maxPlayers;
    private LevelType levelType;
    private Location location;

    public Bot(String name, int protocolVersion, String ip, int port) {
        instance = this;
        this.clientConnection = new ClientConnection(name, protocolVersion, ip, port);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new FramingHandler());
                    ch.pipeline().addLast(new CompressionHandler());
                    ch.pipeline().addLast(new PacketDecoder());
                    ch.pipeline().addLast(new ClientHandler());
                    ch.pipeline().addLast(new PacketEncoder());
                }
            });
            try {
                ChannelFuture f = b.connect(clientConnection.getServerIp(), clientConnection.getServerPort()).sync();
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static Bot get() {
        return instance;
    }
}
