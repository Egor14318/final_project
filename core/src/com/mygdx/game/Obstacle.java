package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle extends Entity {

    public Obstacle(World world, float x, float y) {
        super(world, x, y);
    }

    @Override
    protected Texture createTexture() { return null; }

    @Override
    protected Body createBody(float x, float y) {
            BodyDef bdef = new BodyDef();
            bdef.position.set(x / PPM, y / PPM);

            // МЕНЯЕМ StaticBody на KinematicBody (движущееся тело, на которое не действует гравитация)
            bdef.type = BodyDef.BodyType.KinematicBody;

            Body body = world.createBody(bdef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(
                    sprite.getWidth() / 2f / PPM,
                    sprite.getHeight() / 2f / PPM
            );

            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            fdef.friction = 0.5f;
            body.createFixture(fdef).setUserData("obstacle");

            // ДОБАВЛЯЕМ ЭТУ СТРОКУ: задаем скорость движения ВЛЕВО (отрицательный X)
            // -5f означает 5 метров в секунду (500 пикселей в секунду). Меняй это число для скорости игры.
            body.setLinearVelocity(-5f, 0);

            shape.dispose();
            return body;

    }

    @Override
    protected Sprite createSprite() {
        // ИСПРАВЛЕНО: Раньше тут был return null, из-за чего игра падала
        return new Sprite(new Texture("obstacle.png"));
    }
}