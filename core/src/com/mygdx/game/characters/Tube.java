package com.mygdx.game.characters;

import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;
import static com.mygdx.game.MyGdxGame.SCR_WIDTH;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Bits;

import java.util.Random;

public class Tube {
    int radius = 100;
    int centerY; // Фиксированная высота
    int x;
    int distanceBetweenTubes;
    int speed = 5;
    boolean isPointReceived;
    Texture texture;

    Body lowerBody;

    public Tube(World world, int tubeCount, int tubeIdx){
        // Фиксированная высота: пол (50px) + радиус
        centerY = 50 + radius;

        distanceBetweenTubes = (SCR_WIDTH + radius * 2) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        texture = new Texture("obstaclee.jpg");

        createPhysicsBodies(world);
    }

    private void createPhysicsBodies(World world) {
        FixtureDef fixtureDef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Bird.PPM);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Bits.OBSTACLE;
        fixtureDef.filter.maskBits = Bits.PLAYER;

        BodyDef lowerDef = new BodyDef();
        lowerDef.type = BodyDef.BodyType.KinematicBody;
        lowerDef.position.set((x + radius) / Bird.PPM, centerY / Bird.PPM);
        lowerBody = world.createBody(lowerDef);
        lowerBody.createFixture(fixtureDef);
        lowerBody.setUserData("obstacle");

        shape.dispose();
    }

    public void draw(Batch batch) {
        float centerX = lowerBody.getPosition().x * Bird.PPM;
        float centerYPos = lowerBody.getPosition().y * Bird.PPM;

        batch.draw(texture, centerX - radius, centerYPos - radius, radius * 2, radius * 2);
    }

    public void move(float gameSpeed) {
        x -= speed;

        if (lowerBody != null) {
            lowerBody.setTransform((x + radius) / Bird.PPM, centerY / Bird.PPM, 0);
        }

        if (x < -radius * 2) {
            isPointReceived = false;
            x = SCR_WIDTH + distanceBetweenTubes;
            // centerY остаётся прежним — фиксированная высота
        }
    }

    public boolean isHit(Bird bird) {
        return false;
    }

    public void setPointReceived() {
        isPointReceived = true;
    }

    public boolean needAddPoint(Bird bird) {
        if (bird.body.getPosition().x * Bird.PPM > x + radius && !isPointReceived) {
            return true;
        }
        return false;
    }

    void dispose() {
        texture.dispose();
    }
}