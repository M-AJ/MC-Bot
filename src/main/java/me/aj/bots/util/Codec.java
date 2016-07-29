package me.aj.bots.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import me.aj.bots.bot.Location;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Random;

public class Codec {

    /*
        Utils for reading and writing data.
        Some util methods are copied from various open source projects.
     */

    public static final Charset CHARSET = Charsets.UTF_8;
    public static final Random rand = new Random();

    private Codec() {
    } // Suppress initialization of utility class

    public static String readString(ByteBuf buf) {
        //Reads the length of the string
        int length = readVarInt32(buf);
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        return new String(bytes, CHARSET);
    }

    public static int sizeOf(int integer) {
        return BigInteger.valueOf(integer).toByteArray().length;
    }

    public static void writeString(ByteBuf buf, String string) {
        if (string == null) {
            return;
        }
        //Writes the length of the string
        writeVarInt32(buf, string.length());

        //Writes the bytes of the string
        byte[] bytes = string.getBytes(CHARSET);
        buf.writeBytes(bytes);
    }

    public static int readVarInt32(ByteBuf buf) {
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = buf.readByte();

            out |= (in & 0x7F) << (bytes++ * 7);

            if (bytes > 5) {
                throw new RuntimeException("VarInt too big");
            }

            if ((in & 0x80) != 0x80) {
                break;
            }
        }

        return out;
    }


    public static void writeVarInt32(ByteBuf buf, int value) {
        int part;
        while (true) {
            part = value & 0x7F;

            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }

            buf.writeByte(part);

            if (value == 0) {
                break;
            }
        }
    }

    public static long readVarInt64(ByteBuf buf) {
        //The result we will return
        long result = 0L;

        //How much to indent the current bytes
        int indent = 0;
        long b = (long) buf.readByte();

        //If below, it means there are more bytes
        while ((b & 0x80L) == 0x80) {
            Preconditions.checkArgument(indent < 49, "Too many bytes for a VarInt64.");

            //Adds the byte in the appropriate position (first byte goes last, etc.)
            result += (b & 0x7fL) << indent;
            indent += 7;

            //Reads the next byte
            b = (long) buf.readByte();
        }

        return result += (b & 0x7fL) << indent;
    }

    public static void writeVarInt64(ByteBuf buf, long toEncode) {
        //Loops through until the currently 'selected' set of 7 bits is the terminating one
        while ((toEncode & 0xFFFFFFFFFFFFFF80L) != 0L) {
            /*Writes the selected 7 bits, and adds a 1 at the front
            signifying that there is another byte*/
            buf.writeByte((int) (toEncode & 0x7FL | 0x80L));
            //Selects the next set of 7 bits
            toEncode >>>= 7L;
        }
        //Writes the final terminating byte with a 0 at the front to signify termination
        buf.writeByte((int) (toEncode & 0x7FL));
    }

    public static Location readPosition(ByteBuf byteBuf) {
        long val = byteBuf.readLong();
        double x = val >> 38;
        double y = (val >> 26) & 0xFFF;
        double z = val << 38 >> 38;
        return new Location(x, y, z, 0, 0);
    }

    public static void writePosition(ByteBuf byteBuf, Location l) {
        byteBuf.writeLong((((int) l.getX() & 0x3FFFFFF) << 38) |
                (((int) l.getY() & 0xFFF) << 26) |
                ((int) l.getZ() & 0x3FFFFFF));
    }

    public static byte[] asArray(ByteBuf buf) {
        return asArray(buf, buf.readableBytes());
    }

    public static byte[] asArray(ByteBuf buf, int length) {
        byte[] bytes = new byte[length];
        buf.getBytes(buf.readerIndex(), bytes);
        return bytes;
    }

    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public static boolean readableVarInt(ByteBuf buf) {
        if (buf.readableBytes() > 5) {
            return true;
        }

        int idx = buf.readerIndex();
        byte in;
        do {
            if (buf.readableBytes() < 1) {
                buf.readerIndex(idx);
                return false;
            }
            in = buf.readByte();
        } while ((in & 0x80) != 0);

        buf.readerIndex(idx);
        return true;
    }

    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}