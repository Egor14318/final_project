package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.mygdx.game.screens.ScreenRestart;
import com.mygdx.game.screens.ScreenMenu;
import com.mygdx.game.screens.ScreenGame;

public class MyGdxGame extends Game  {
    public static final int SCR_WIDTH = 1280;
    public static final int SCR_HEIGHT = 720;
    public OrthographicCamera camera;
    public SpriteBatch batch;


    public ScreenRestart screenRestart;
    public ScreenGame screenGame;

    public ScreenMenu screenMenu;


    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);


        screenMenu = new ScreenMenu(this);
        screenGame = new ScreenGame(this);
        screenRestart =  new ScreenRestart(this	);
        setScreen(screenMenu);
    }








    @Override
    public void dispose () {
        batch.dispose();


    }

}


