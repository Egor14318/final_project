package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game._utils._Constants;

public class AssetManager {
    private static AssetManager instance;

    public Texture textureBackground;
    // ... другие текстуры ...

    private AssetManager() {}

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public void load() {
        // 🔹 Загрузка текстуры фона
        if (!_Constants.PATH_TEXTURE_BACKGROUND.isEmpty()) {
            textureBackground = new Texture(Gdx.files.internal(_Constants.PATH_TEXTURE_BACKGROUND));
            textureBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public void dispose() {
        if (textureBackground != null) textureBackground.dispose();
    }
}