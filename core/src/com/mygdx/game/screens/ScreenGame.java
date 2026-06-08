package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.characters.Bird;
import com.mygdx.game.characters.Floor;
import com.mygdx.game.characters.Tube;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.MyGdxGame;

public class ScreenGame implements Screen {

    ScreenRestart screenRestart;
    Bird bird;
    Floor floor;
    MovingBackground background;
    WorldManifold worldManifold;
    int gamePoints;
    boolean isGameOver;
    MyGdxGame myGdxGame;
    Tube[] tubes;
    int tubeCount = 2;

    World world;
    Box2DDebugRenderer debugRenderer;

    float targetX; // Целевая позиция игрока по X
    float gameSpeed = 10f;
    float targetY;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        world = new World(new com.badlogic.gdx.math.Vector2(0, -15f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Игрок спавнится слева (x=100)
        bird = new Bird(world, 100, 200, 160, 160);
        floor = new Floor(world, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);

        background = new MovingBackground("background.jpg");

        // Целевые позиции: X чуть левее центра, Y чуть ниже центра
        targetX = MyGdxGame.SCR_WIDTH * 0.35f;  // 35% от ширины
        targetY = MyGdxGame.SCR_HEIGHT * 0.4f;  // 40% от высоты

        initTubes();

        // Обработчик столкновений через beginContact/endContact
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();

                Object udA = fa.getUserData();
                Object udB = fb.getUserData();

                // Сенсор под ногами коснулся пола → игрок на земле
                if ((udA != null && udA.equals("groundSensor")) ||
                        (udB != null && udB.equals("groundSensor"))) {
                    bird.setOnGround(true);
                }

                // Игрок столкнулся с препятствием → game over
                if ((udA != null && udA.equals("obstacle")) ||
                        (udB != null && udB.equals("obstacle"))) {
                    isGameOver = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();

                Object udA = fa.getUserData();
                Object udB = fb.getUserData();

                // Сенсор оторвался от пола → игрок в воздухе
                if ((udA != null && udA.equals("groundSensor")) ||
                        (udB != null && udB.equals("groundSensor"))) {
                    bird.setOnGround(false);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }
    @Override
    public void show() {
        isGameOver = false;
        gamePoints = 0;
        gameSpeed = 10f;


        bird.body.setTransform(100 / Bird.PPM, targetY / Bird.PPM, 0);
        bird.body.setLinearVelocity(0, 0);
        bird.setOnGround(false);

        initTubes();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched() && !isGameOver) {
            bird.onClick();
        }

        world.step(1 / 60f, 6, 2);


        bird.updateVerticalBehavior(targetY);
        bird.updateHorizontalBehavior(targetX, delta); // <-- новое


        gameSpeed = MathUtils.clamp(gameSpeed + delta * 0.5f, 10f, 25f);

        background.move();

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        for (Tube tube : tubes) {
            tube.move(gameSpeed);
            if (tube.needAddPoint(bird)) {
                gamePoints += 1;
                tube.setPointReceived();
            }
        }

        if (isGameOver) {
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
            return;
        }

        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        floor.draw(myGdxGame.batch);
        bird.draw(myGdxGame.batch);
        for (Tube tube : tubes) tube.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    public void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(world, tubeCount, i);
        }
    }

    @Override
    public void dispose() {
        bird.dispose();
        floor.dispose();
        background.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}