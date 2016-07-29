package me.aj.bots.protocol.packets.play;

import io.netty.buffer.ByteBuf;
import me.aj.bots.protocol.packets.PacketIn;
import me.aj.bots.Bot;
import me.aj.bots.bot.Difficulty;
import me.aj.bots.bot.Dimension;
import me.aj.bots.bot.GameMode;
import me.aj.bots.bot.LevelType;
import me.aj.bots.util.Codec;

public class PacketInJoinGame extends PacketIn {

    private int eid;

    private short gamemode, difficulty, maxplayers;

    private byte dimenension;

    private String levelType;

    private boolean reducesDI;

    public PacketInJoinGame(Bot bot) {
        super(bot);
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.IN;
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.eid = byteBuf.readInt();
        this.gamemode = byteBuf.readUnsignedByte();
        this.dimenension = byteBuf.readByte();
        this.difficulty = byteBuf.readUnsignedByte();
        this.maxplayers = byteBuf.readUnsignedByte();
        this.levelType = Codec.readString(byteBuf);
        this.reducesDI = byteBuf.readBoolean();
    }

    @Override
    public void handle() {
        Bot.get().setEntityId(eid);
        Bot.get().setGameMode(GameMode.fromShort(gamemode));
        Bot.get().setDimension(Dimension.fromByte(dimenension));
        Bot.get().setDifficulty(Difficulty.fromShort(difficulty));
        Bot.get().setMaxPlayers(maxplayers);
        Bot.get().setLevelType(LevelType.fromName(levelType));

        System.out.print("\nReceived Join Game. ");
        System.out.print("\nEID: " + eid + " GM: " + gamemode +
                " DIM: " + dimenension + " DIFF: " + difficulty
                + " MP: " + maxplayers + " LT: " + levelType
                + " RDI: " + reducesDI);
    }


    public int getId() {
        return 0x01;
    }

    public State getState() {
        return State.PLAY;
    }
}
