package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class contactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa == null || fb == null) return;

        Object udA = fa.getUserData();
        Object udB = fb.getUserData();

        // Проверяем, что это строки-теги
        if (!(udA instanceof String) || !(udB instanceof String)) return;

        String tagA = (String) udA;
        String tagB = (String) udB;

        // Достаем сами Java-объекты из Body (а не из Fixture!)
        Object bodyUdA = fa.getBody().getUserData();
        Object bodyUdB = fb.getBody().getUserData();

        // 1. Касание земли
        if (("ball_foot".equals(tagA) && "ground".equals(tagB)) ||
                ("ground".equals(tagA) && "ball_foot".equals(tagB))) {
            Ball ball = (bodyUdA instanceof Ball) ? (Ball) bodyUdA : (Ball) bodyUdB;
            ball.setGrounded(true);
        }

        // 2. Столкновение с препятствием
        if (("ball_foot".equals(tagA) && "obstacle".equals(tagB)) ||
                ("obstacle".equals(tagA) && "ball_foot".equals(tagB))) {
            System.out.println("Врезался в препятствие! Game Over!");
            // GameStateManager.onObstacleHit(); // Можно вызвать твой менеджер
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa == null || fb == null) return;

        Object udA = fa.getUserData();
        Object udB = fb.getUserData();
        if (!(udA instanceof String) || !(udB instanceof String)) return;

        String tagA = (String) udA;
        String tagB = (String) udB;
        Object bodyUdA = fa.getBody().getUserData();
        Object bodyUdB = fb.getBody().getUserData();

        // Отрыв от земли (начало падения)
        if (("ball_foot".equals(tagA) && "ground".equals(tagB)) ||
                ("ground".equals(tagA) && "ball_foot".equals(tagB))) {
            Ball ball = (bodyUdA instanceof Ball) ? (Ball) bodyUdA : (Ball) bodyUdB;
            ball.setGrounded(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}