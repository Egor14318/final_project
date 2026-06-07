package com.mygdx.game._entities;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game._utils._Constants;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game._utils._Constants;

public class Obstacle {
    private Body body;

    public Obstacle(World world, float x, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, _Constants.GROUND_HEIGHT + height / 2);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(_Constants.OBSTACLE_WIDTH / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData("obstacle");
        body.setLinearVelocity(-_Constants.OBSTACLE_SPEED, 0);
    }

    public void update(float delta) {
        // Препятствие движется автоматически через setLinearVelocity
    }

    public boolean isOffScreen() {
        return body.getPosition().x < -2;
    }

    public Body getBody() {
        return body;
    }

    public void destroy(World world) {
        world.destroyBody(body);
    }
}