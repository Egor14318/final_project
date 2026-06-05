package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Background {


        Texture texture;
        int texture1X, texture2X;
        int speed = 2;

        public void background(String pathToTexture) {
            texture1X = 0;
            texture2X = GameSettings.SCREEN_WIDTH;
            texture = new Texture(pathToTexture);

        }


        public void move() {

            texture1X -= speed;
            texture2X -= speed;
            if (texture1X <= -GameSettings.SCREEN_WIDTH) {
                texture1X = GameSettings.SCREEN_WIDTH;

            }
            if (texture2X <= -GameSettings.SCREEN_WIDTH) {
                texture2X = GameSettings.SCREEN_WIDTH;
            }
        }

        public void draw(Batch batch) {
            batch.draw(texture, texture1X, 0, GameSettings.SCREEN_WIDTH + 5, GameSettings.SCREEN_WIDTH);
            batch.draw(texture, texture2X, 0, GameSettings.SCREEN_WIDTH + 5, GameSettings.SCREEN_WIDTH);
        }

        public void dispose() {

        }

    }


