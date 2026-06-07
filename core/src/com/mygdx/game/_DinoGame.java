package com.mygdx.game;




import com.badlogic.gdx.Game;
import com.mygdx.game.managers.AssetManager;
import com.mygdx.game._screens.GameScreen;

public class _DinoGame extends Game {

    @Override
    public void create() {
        // Загрузка ресурсов
        AssetManager.getInstance().load();

        // Запуск игрового экрана
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.getInstance().dispose();
    }
}