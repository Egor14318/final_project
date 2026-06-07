package com.mygdx.game._utils;



import com.badlogic.gdx.math.Vector2;

public class _Constants {
    // Физика и масштабирование
    public static final float PPM = 100.0f; // Pixels Per Meter
    public static final Vector2 GRAVITY = new Vector2(0, -20f);
    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    // Экран
    public static final float WORLD_WIDTH = 800f;
    public static final float WORLD_HEIGHT = 480f;

    // Игрок (шар)
    public static final float PLAYER_RADIUS = 0.5f;
    public static final float PLAYER_X = 2f;
    public static final float PLAYER_Y = 1f;
    public static final float JUMP_VELOCITY = 8f;

    // Препятствия
    public static final float OBSTACLE_WIDTH = 0.5f;
    public static final float OBSTACLE_HEIGHT_MIN = 1f;
    public static final float OBSTACLE_HEIGHT_MAX = 2f;
    public static final float OBSTACLE_SPEED = 5f;
    public static final float SPAWN_DISTANCE = 10f;
    public static final float MIN_SPAWN_INTERVAL = 1.5f;
    public static final float MAX_SPAWN_INTERVAL = 3f;

    // Фон
    public static final float BACKGROUND_SPEED = 2f;

    // Земля
    public static final float GROUND_HEIGHT = 1f;

    // Пути к ресурсам (пустые для будущей интеграции)
    public static final String PATH_TEXTURE_PLAYER = "BALL.PNG";
    public static final String PATH_TEXTURE_BACKGROUND = "background.jpg";
    public static final String PATH_TEXTURE_OBSTACLE = "obstacle.png";
    public static final String PATH_TEXTURE_GROUND = "";

    // Будущие пути для UI и звуков
    public static final String PATH_MUSIC_GAME = "";
    public static final String PATH_MUSIC_MENU = "";
    public static final String PATH_SOUND_JUMP = "";
    public static final String PATH_SOUND_HIT = "";
    public static final String PATH_SOUND_BUTTON = "";
    public static final String PATH_UI_PAUSE = "";
    public static final String PATH_UI_PLAY = "";
    public static final String PATH_UI_VOLUME = "";
}