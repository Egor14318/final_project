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
import com.mygdx.game.components.TextView;

public class ScreenGame implements Screen {

    Bird bird;
    Floor floor;
    MovingBackground background;
    TextView poit;
    int gamePoints;
    boolean isGameOver;
    MyGdxGame myGdxGame;
    Tube[] tubes;
    int tubeCount = 2;

    World world;
    Box2DDebugRenderer debugRenderer;

    float targetX;
    float gameSpeed = 10f;
    float targetY;
    float poits;
    int pont;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        debugRenderer = new Box2DDebugRenderer();
        background = new MovingBackground("background.jpg");
        poit = new TextView(myGdxGame.commonWhiteFont, 20, 30, "0");

        targetX = MyGdxGame.SCR_WIDTH * 0.35f;
        targetY = MyGdxGame.SCR_HEIGHT * 0.3f;

        // Инициализация при первом запуске
        initGame();
    }

    /**
     * Полная инициализация/сброс игры.
     * Вызывается при первом запуске и при каждом рестарте.
     */
    private void initGame() {
        // Уничтожаем старый мир, если он есть
        if (world != null) {
            world.dispose();
        }

        // Создаём новый физический мир
        world = new World(new com.badlogic.gdx.math.Vector2(0, -15f), true);

        // Создаём новые тела
        bird = new Bird(world, 100, 200, 160, 160);
        floor = new Floor(world, MyGdxGame.SCR_WIDTH, MyGdxGame.SCR_HEIGHT);

        initTubes();

        // Устанавливаем ContactListener
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();
                Object udA = fa.getUserData();
                Object udB = fb.getUserData();

                if ((udA != null && udA.equals("groundSensor")) ||
                        (udB != null && udB.equals("groundSensor"))) {
                    bird.setOnGround(true);
                }

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

                if ((udA != null && udA.equals("groundSensor")) ||
                        (udB != null && udB.equals("groundSensor"))) {
                    bird.setOnGround(false);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    @Override
    public void show() {
        // При каждом входе на экран — полный сброс
        isGameOver = false;
        gamePoints = 0;
        gameSpeed = 10f;
        poits = 0;
        pont = 0;

        // Пересоздаём мир и все тела
        initGame();
    }

    public String pointCounter() {
        poits += 0.01;
        pont = (int) poits;
        return Integer.toString(pont);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched() && !isGameOver) {
            bird.onClick();
        }

        world.step(1 / 60f, 6, 2);

        bird.updateVerticalBehavior(targetY);
        bird.updateHorizontalBehavior(targetX, delta);

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

        // Выход за левую границу → game over
        if ((bird.body.getPosition().x * Bird.PPM) - (bird.width / 2f) < 0) {
            isGameOver = true;
        }

        if (isGameOver) {
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
            return;
        }

        // Обновляем счётчик на экране
        poit.setText(pointCounter());

        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        bird.draw(myGdxGame.batch);
        poit.draw(myGdxGame.batch);
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
        if (bird != null) bird.dispose();
        if (floor != null) floor.dispose();
        if (background != null) background.dispose();
        if (world != null) world.dispose();
        if (debugRenderer != null) debugRenderer.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}