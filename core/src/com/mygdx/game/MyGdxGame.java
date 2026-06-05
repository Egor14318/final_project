package com.mygdx.game;


import static java.awt.Color.WHITE;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameScreen;


import java.awt.Menu;



public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public GameScreen gameScreen;

    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;
    public BitmapFont largeWhiteFont;
    public BitmapFont largeWhiteFontMenu;

    public GameSettings gameSettings;

    public Vector3 touch;
    public World world;
    float accumulator = 0;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        gameScreen = new GameScreen();

        setScreen((Screen) gameScreen);

    }

/*
    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

*/
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();

    }
}