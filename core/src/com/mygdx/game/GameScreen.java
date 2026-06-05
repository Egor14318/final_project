package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    MyGdxGame myGdxGame;
    Background background;


    public GameScreen(){
        this.myGdxGame = myGdxGame;
        background = new Background();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    background.move();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
