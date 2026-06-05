package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
    protected Sprite sprite;
    protected Body body;
    protected World world;

    // Флаг для безопасного удаления
    protected boolean toRemove = false;

    public static final float PPM = 100.0f;

    public Entity(World world, float x, float y) {
        this.world = world;


        this.sprite = createSprite();


        this.body = createBody(x / PPM, y / PPM);


        this.body.setUserData(this);
    }

    protected abstract Texture createTexture();
    protected abstract Body createBody(float x, float y);
    protected abstract Sprite createSprite();

    public void update(float dt) {
        if (body == null || toRemove) return;

        sprite.setPosition(
                body.getPosition().x * PPM - sprite.getWidth() / 2,
                body.getPosition().y * PPM - sprite.getHeight() / 2
        );
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public void render(SpriteBatch batch) {
        if (!toRemove && sprite != null) {
            sprite.draw(batch);
        }
    }

    public void destroy() {
        if (body != null && !toRemove) {
            world.destroyBody(body);
            body = null;
            toRemove = true;
        }
    }


    public boolean isToRemove() { return toRemove; }
    public void setToRemove(boolean toRemove) { this.toRemove = toRemove; }
    public Body getBody() { return body; }
    public Sprite getSprite() { return sprite; }
}