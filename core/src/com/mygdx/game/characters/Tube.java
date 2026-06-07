package com.mygdx.game.characters;




import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;
import static com.mygdx.game.MyGdxGame.SCR_WIDTH;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

import com.mygdx.game.MyGdxGame;

public class Tube {
    int width = 200;
    int height = 700;
    int gapHeight = 400;
    int padding = 100;
    int gapY;
    int x;
    int distanceBetweenTubes;
    int speed = 10;
    boolean isPointReceived;
    Random random = new Random();
    Texture textureUpperTube;
    Texture textureDownTube;

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();

        gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        distanceBetweenTubes = (SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        textureUpperTube = new Texture("obstacle.png");
        textureDownTube = new Texture("obstacle.png");
    }

    public void draw(Batch batch) {
        batch.draw(textureUpperTube, x, gapY + gapHeight / 2, width, height);
        batch.draw(textureDownTube, x, gapY - gapHeight / 2 - height, width, height);
    }

    public void move() {
        x -= speed;
        if (x < -width) {
            isPointReceived = false;
            x = SCR_WIDTH + distanceBetweenTubes;
            gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        }
    }

    public boolean isHit(Bird bird) {

        // down tube collision
        if (bird.y <= gapY - gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x)
            return true;
        if (bird.y + bird.height >= gapY + gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x)
            return true;
        // upper tube collision
        // сделать проверку самостоятельно тут

        return false;
    }


    public void setPointReceived() {
        isPointReceived = true;

    }

    public boolean needAddPoint(Bird bird) {
        if (bird.x > x + width && !isPointReceived) {
            return true;
        }

        return false;

    }

    /*
    public boolean isHit(Bird bird) {
        if (bird.x + bird.width == x && bird.x == x + width) {
            return true;
        }
        return false;
    }
    */
    void dispose() {
        textureDownTube.dispose();
        textureUpperTube.dispose();
    }


}