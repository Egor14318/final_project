package com.mygdx.game.characters;

import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;
import static com.mygdx.game.MyGdxGame.SCR_WIDTH;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Bits;

import java.util.Random;

public class Tube {
    int width = 200;
    int height = 700;
    int gapHeight = 400;
    int padding = 100;
    int gapY;
    int x;
    int distanceBetweenTubes;
    int speed = 5;
    boolean isPointReceived;
    Random random = new Random();
    Texture textureUpperTube;
    Texture textureDownTube;

    Body upperBody;
    Body lowerBody;

    public Tube(World world, int tubeCount, int tubeIdx){
        random = new Random();

        gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        distanceBetweenTubes = (SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        //textureUpperTube = new Texture("obstacle.png");
        textureDownTube = new Texture("obstacle.png");


        createPhysicsBodies(world);
    }


    private void createPhysicsBodies(World world) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2f) / Bird.PPM, (height / 2f) / Bird.PPM);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Bits.OBSTACLE;
        fixtureDef.filter.maskBits = Bits.PLAYER; // Препятствие сталкивается только с игроком

        // Верхнее препятствие
        //BodyDef upperDef = new BodyDef();
        //upperDef.type = BodyDef.BodyType.KinematicBody;
       // upperDef.position.set((x + width / 2f) / Bird.PPM, (gapY + gapHeight / 2f + height / 2f) / Bird.PPM);
        //upperBody = world.createBody(upperDef);
        //upperBody.createFixture(fixtureDef);
        //upperBody.setUserData("obstacle");

        // Нижнее препятствие
        BodyDef lowerDef = new BodyDef();
        lowerDef.type = BodyDef.BodyType.KinematicBody;
        lowerDef.position.set((x + width / 2f) / Bird.PPM, (gapY - gapHeight / 2f - height / 2f) / Bird.PPM);
        lowerBody = world.createBody(lowerDef);
        lowerBody.createFixture(fixtureDef);
        lowerBody.setUserData("obstacle");

        shape.dispose();
    }

    public void draw(Batch batch) {
         //batch.draw(textureUpperTube, x, gapY + gapHeight / 2, width, height);
        batch.draw(textureDownTube, x, gapY - gapHeight / 2 - height, width, height);
    }

    public void move(float gameSpeed) {
        x -= speed;

        // Обновляем позиции физических тел
        //if (upperBody != null) {
            //upperBody.setTransform((x + width / 2f) / Bird.PPM, (gapY + gapHeight / 2f + height / 2f) / Bird.PPM, 0);
        //}
        if (lowerBody != null) {
            lowerBody.setTransform((x + width / 2f) / Bird.PPM, (gapY - gapHeight / 2f - height / 2f) / Bird.PPM, 0);
        }

        if (x < -width) {
            isPointReceived = false;
            x = SCR_WIDTH + distanceBetweenTubes;
            gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        }
    }

    public boolean isHit(Bird bird) {
        //todo
        // Теперь столкновения обрабатываются через ContactListener (надо сделать, 09.06.26 2:34 я спать )
        return false;
    }

    public void setPointReceived() {
        isPointReceived = true;
    }

    public boolean needAddPoint(Bird bird) {
        if (bird.body.getPosition().x * Bird.PPM > x + width && !isPointReceived) {
            return true;
        }
        return false;
    }

    void dispose() {
        textureDownTube.dispose();
        textureUpperTube.dispose();
        // world.dispose() не нужен с ним больше проблем чем пользы
    }
}