package com.mygdx.game._screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game._DinoGame;

public class _MainMenuScreen implements Screen {
    private _DinoGame game;
    private Stage stage;
    private Skin skin;
    GameScreen gameScreen;

    public _MainMenuScreen(final _DinoGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("DINO BALL", skin, "title");
        TextButton btnPlay = new TextButton("PLAY", skin);
        TextButton btnExit = new TextButton("EXIT", skin);
        gameScreen = new GameScreen(game);

        btnPlay.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                // TODO: Gdx.audio.newSound(_AssetPaths.SOUND_CLICK).play();
                game.setScreen(new GameScreen(game));
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) { Gdx.app.exit(); }
        });

        table.add(title).padBottom(40).row();
        table.add(btnPlay).width(200).height(50).padBottom(20).row();
        table.add(btnExit).width(200).height(50);
        stage.addActor(table);
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}