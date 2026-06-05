package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball extends Entity {
    private boolean isGrounded = false;

    public Ball(World world, float x, float y) {
        super(world, x, y);
    }

    @Override
    protected Texture createTexture() {
        return null; // Не используется, т.к. мы создаем Sprite напрямую
    }

    @Override
    protected Body createBody(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        CircleShape shape = new CircleShape();
        shape.setRadius(30 / PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.5f;
        fdef.restitution = 0;
        body.createFixture(fdef).setUserData("ball_foot");

        shape.dispose();
        return body;
    }

    @Override
    protected Sprite createSprite() {
        // Внимание: имя файла должно точно совпадать с тем, что в папке assets (регистр важен!)
        Sprite s = new Sprite(new Texture("BALL.png"));
        // Диаметр = радиус * 2. Если радиус 25, то диаметр 50 (в метрах Box2D).
        // Умножаем на PPM, чтобы получить пиксели: 50 * 100 = 5000? Нет, мы задаем размер в метрах для спрайта,
        // но libGDX сам масштабирует. Проще всего задать размер в пикселях:
        s.setSize(75, 75); // 50 пикселей ширина и высота (подбери под свою картинку)
        return s;
    }

    public void jump() {
        if (isGrounded) {
            // 1. Обнуляем вертикальную скорость, чтобы прыжок не накапливался
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            // 2. Прикладываем импульс. Если прыжок слишком слабый, увеличь 1.2f до 1.5f
            body.applyLinearImpulse(new Vector2(0, 5.2f), body.getWorldCenter(), true);
            isGrounded = false;
        }
    }

    public void setGrounded(boolean grounded) {
        this.isGrounded = grounded;
    }
}