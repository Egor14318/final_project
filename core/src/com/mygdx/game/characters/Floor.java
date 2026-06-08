package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Bits;

public class Floor {
    Body body;
    Texture texture;
    int width;
    int height;

    public Floor(World world, int screenWidth, int screenHeight) {
        this.width = screenWidth;
        this.height = 50;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((screenWidth / 2f) / Bird.PPM, (height / 2f) / Bird.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((screenWidth / 2f) / Bird.PPM, (height / 2f) / Bird.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = Bits.FLOOR;
        fixtureDef.filter.maskBits = Bits.PLAYER; // Пол взаимодействует только с игроком

        body.createFixture(fixtureDef);
        body.setUserData("floor");
        shape.dispose();

        texture = new Texture("background.jpg");
    }

    public void draw(Batch batch) {
        batch.draw(texture, 0, 0, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}