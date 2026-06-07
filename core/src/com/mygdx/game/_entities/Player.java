package com.mygdx.game._entities;




import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game._utils._Constants;

public class Player {
    private Body body;
    private boolean canJump = false;

    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(_Constants.PLAYER_X, _Constants.PLAYER_Y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(_Constants.PLAYER_RADIUS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();

        // Для определения прыжка
        body.setUserData("player");
    }

    public void jump() {
        if (canJump) {
            body.setLinearVelocity(0, 0);
            body.applyLinearImpulse(
                    0,
                    _Constants.JUMP_VELOCITY,
                    body.getWorldCenter().x,
                    body.getWorldCenter().y,
                    true
            );
            canJump = false;
        }
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public boolean isOnGround() {
        return canJump;
    }

    public void update(float delta) {
        // Логика обновления игрока
    }

    public Body getBody() {
        return body;
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }
}