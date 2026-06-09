package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.ScreenRestart;

public class Bird {
    public Body body;
    public Fixture groundSensor;
    public static int width;
    public static int height;
    public ScreenRestart screenRestart;
    boolean isOnGround = false;
    public static final float PPM = 100f;

    TextureRegion texture;

    // todo Параметры горизонтального движения (на запас)
    private float currentSpeedX = 0f;
    private float targetSpeedX = 0f;
    private float speedAcceleration = 15f;

    public Bird(World world, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        texture = new TextureRegion(new Texture("BALL.png"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        body = world.createBody(bodyDef);

        // ОСНОВНОЕ ТЕЛО
        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox((width / 2f) / PPM, (height / 2f) / PPM);

        FixtureDef bodyFixture = new FixtureDef();
        bodyFixture.shape = bodyShape;
        bodyFixture.density = 1.0f;
        bodyFixture.friction = 0.0f;
        bodyFixture.restitution = 0.0f;
        bodyFixture.filter.categoryBits = 0x0001;
        bodyFixture.filter.maskBits = 0x0002 | 0x0004;
        body.createFixture(bodyFixture);
        body.setUserData("player");
        bodyShape.dispose();

        // СЕНСОР ПОД НОГАМИ
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox((width * 0.4f) / PPM, 5f / PPM,
                new com.badlogic.gdx.math.Vector2(0, -(height / 2f + 2) / PPM), 0);

        FixtureDef sensorFixture = new FixtureDef();
        sensorFixture.shape = sensorShape;
        sensorFixture.isSensor = true;
        sensorFixture.filter.categoryBits = 0x0001;
        sensorFixture.filter.maskBits = 0x0002;
        groundSensor = body.createFixture(sensorFixture);
        groundSensor.setUserData("groundSensor");
        sensorShape.dispose();

        body.setFixedRotation(true);
    }

    public void onClick() {
        if (isOnGround) {
            body.setLinearVelocity(body.getLinearVelocity().x, 15);
            isOnGround = false;
        }
    }


    public void updateHorizontalBehavior(float targetX, float delta) {
        float currentX = body.getPosition().x * PPM;
        float currentVx = body.getLinearVelocity().x;

        //  ПРУЖИННА: тянет к targetX, пропорционально расстоянию
        float distanceToTarget = targetX - currentX;
        float springForce = distanceToTarget * 0.5f; // Коэф. жёсткости пружины

        // 2. ДЕМПФЕР: гасит колебания, чтобы не качался туда-сюда
        float dampingForce = -currentVx * 4.0f; // Коэф. трения о "воздух"

        // 3. Общая сила по X
        float totalForceX = springForce + dampingForce;

        // Ограничение максимальную силу, для отсутствия рывков, и телепортации из-за столба (больше 40 сильно меньше 20 не успевает)
        totalForceX = MathUtils.clamp(totalForceX, -30f, 30f);

        // Применение силы
        body.applyForceToCenter(totalForceX * body.getMass(), 0, true);

        // 4. Ограничиваем максимальную  скорость
        float maxSpeedX = 4f; // Максимум 4 м/с (или у.с. хз) по горизонтали
        if (Math.abs(currentVx) > maxSpeedX) {
            body.setLinearVelocity(Math.signum(currentVx) * maxSpeedX, body.getLinearVelocity().y);
        }
    }


     // todo Изменяет текущую скорость по X (можно вызвать при сборе бонусов и т.п. или для контроля центра экрана ( этого не будет) ))

    public void addSpeedX(float delta) {
        targetSpeedX += delta;
    }

    public void reduceSpeedX(float delta) {
        targetSpeedX -= delta;
    }


     //todo  Прямое изменение горизонтальной скорости (резкое ускорение/замедление) на запас

    public void boostSpeedX(float boost) {
        float vy = body.getLinearVelocity().y;
        float vx = body.getLinearVelocity().x + boost;
        vx = MathUtils.clamp(vx, -8f, 8f);
        body.setLinearVelocity(vx, vy);
    }

    public void updateVerticalBehavior(float targetY) {
        if (isOnGround) return;

        float currentY = body.getPosition().y * PPM;
        float distanceToTarget = targetY - currentY;

        if (Math.abs(distanceToTarget) > 15f) {
            float force = distanceToTarget * 0.15f;
            force = MathUtils.clamp(force, -15f, 15f);
            body.applyForceToCenter(0, force * body.getMass(), true);
        }

        float vy = body.getLinearVelocity().y;
        if (vy < -12f) body.setLinearVelocity(body.getLinearVelocity().x, -12f);
        if (vy > 15f) body.setLinearVelocity(body.getLinearVelocity().x, 15f);
    }

    public void setOnGround(boolean onGround) {
        this.isOnGround = onGround;
    }

    public void draw(Batch batch) {
        float x = body.getPosition().x * PPM;
        float y = body.getPosition().y * PPM;

        float angle = body.getLinearVelocity().y * 2.5f;
        angle = MathUtils.clamp(angle, -30, 30);

        batch.draw(texture, x - width / 2f, y - height / 2f,
                width / 2f, height / 2f, width, height, 1, 1, angle);
    }



    public void dispose() {
        texture.getTexture().dispose();
    }


}