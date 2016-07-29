package me.aj.bots.bot;

import lombok.Getter;

@Getter
public class Location {

    private double x, y, z;
    private float yaw, pitch;

    public Location(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return "x:" + x + "y:" + y + "z:" + z;
    }
}
