package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;


public class ball {
    Body ballBody;
    CircleShape circleShape;
    World world;

    public ball() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Делаем шар подвижным (подчиняющимся физике)
        bodyDef.position.set(55, 55);
        ballBody = world.createBody(bodyDef);
        circleShape = new CircleShape();
        circleShape.setRadius(1.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1.0f;    // Плотность( массА)
        fixtureDef.friction = 0.2f;   // Коэффициент трения
        fixtureDef.restitution = 0.6f;
        ballBody.createFixture(fixtureDef);


    }


    public void dispose(){
        circleShape.dispose();
    }
}
