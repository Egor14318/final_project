package com.mygdx.game;

public class Bits {
    public static final short PLAYER   = 0x0001; // 0001
    public static final short FLOOR    = 0x0002; // 0010
    public static final short OBSTACLE = 0x0004; // 0100
    public static final short SENSOR_GROUND = 0x0008; // 1000 (для сенсора под ногами)
}