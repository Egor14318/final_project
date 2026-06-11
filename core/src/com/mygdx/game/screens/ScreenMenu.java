package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.TextButton;
import com.mygdx.game.components.TextView;


public class ScreenMenu implements Screen {
    MyGdxGame myGdxGame;
    Batch batch;
    TextButton buttonExit;
    TextButton buttonStart;
    MovingBackground background;



    TextView titleView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;



    public ScreenMenu(MyGdxGame myGdxGame) {
        this.myGdxGame  = myGdxGame;

        background = new MovingBackground("background.jpg");
        buttonExit = new TextButton(50,400,"Exit");
        buttonStart = new TextButton(600,400,"Start");
        startButtonView = new ButtonView(600, 256, 440, 70, myGdxGame.commonWhiteFont, myGdxGame.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.commonWhiteFont, myGdxGame.BUTTON_LONG_BG_IMG_PATH, "setting");
        exitButtonView = new ButtonView(50, 256, 440, 70, myGdxGame.commonWhiteFont, myGdxGame.BUTTON_LONG_BG_IMG_PATH, "exit");



    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched())
        {
            Vector3 touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (buttonStart.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            }
            if (buttonExit.isHit((int) touch.x, (int) touch.y)) {
                Gdx.app.exit();
            }
            if (startButtonView.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            }
            if (exitButtonView.isHit((int) touch.x, (int) touch.y)) {
                Gdx.app.exit();
            }
        }



        ScreenUtils.clear(1, 0, 0, 1);

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        //buttonExit.draw(myGdxGame.batch);
        //buttonStart.draw(myGdxGame.batch);
        startButtonView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);

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
        buttonStart.dispose();
        buttonExit.dispose();
        background.dispose();

    }
}


