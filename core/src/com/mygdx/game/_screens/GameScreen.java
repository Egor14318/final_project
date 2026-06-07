package com.mygdx.game._screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game._DinoGame;
import com.mygdx.game._entities.Obstacle;
import com.mygdx.game._entities.Player;
import com.mygdx.game._physics.PhysicsWorld;
import com.mygdx.game._utils._Constants;
import com.mygdx.game.managers.AssetManager;

public class GameScreen implements Screen, ContactListener {
    private _DinoGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private PhysicsWorld physicsWorld;
    private Player player;
    private Array<Obstacle> obstacles;

    private float spawnTimer;
    private float nextSpawnTime;

    // Для циклического фона
    private float backgroundX1 = 0;
    private float backgroundX2;

    public GameScreen(_DinoGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                _Constants.WORLD_WIDTH / _Constants.PPM,
                _Constants.WORLD_HEIGHT / _Constants.PPM);

        batch = new SpriteBatch();
        physicsWorld = new PhysicsWorld();
        physicsWorld.getWorld().setContactListener(this);

        player = new Player(physicsWorld.getWorld());
        obstacles = new Array<>();

        spawnTimer = 0;
        nextSpawnTime = _Constants.MIN_SPAWN_INTERVAL;

        backgroundX2 = _Constants.WORLD_WIDTH / _Constants.PPM;
    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(0.5f, 0.7f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновление физики
        physicsWorld.update(delta);

        // Обработка ввода
        handleInput();

        // Обновление фона (циклический скроллинг)
        updateBackground(delta);

        // Спавн препятствий
        spawnTimer += delta;
        if (spawnTimer >= nextSpawnTime) {
            spawnObstacle();
            spawnTimer = 0;
            nextSpawnTime = _Constants.MIN_SPAWN_INTERVAL +
                    (float)(Math.random() * (_Constants.MAX_SPAWN_INTERVAL - _Constants.MIN_SPAWN_INTERVAL));
        }

        // Обновление препятствий
        updateObstacles();

        // Отрисовка
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawBackground();
        batch.end();

        physicsWorld.render();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            player.jump();
        }
    }

    private void updateBackground(float delta) {
        float speed = _Constants.BACKGROUND_SPEED * delta / _Constants.PPM;
        backgroundX1 -= speed;
        backgroundX2 -= speed;

        float width = _Constants.WORLD_WIDTH / _Constants.PPM;
        if (backgroundX1 <= -width) {
            backgroundX1 = backgroundX2 + width;
        }
        if (backgroundX2 <= -width) {
            backgroundX2 = backgroundX1 + width;
        }
    }

    private void drawBackground() {
        // 🔹 Отрисовка циклического фона
        if (AssetManager.getInstance().textureBackground != null) {
            Texture bg = AssetManager.getInstance().textureBackground;
            float worldWidth = _Constants.WORLD_WIDTH / _Constants.PPM;
            float worldHeight = _Constants.WORLD_HEIGHT / _Constants.PPM;

            // Рисуем две копии текстуры для циклического скроллинга
            batch.draw(bg, backgroundX1, 0, worldWidth, worldHeight);
            batch.draw(bg, backgroundX2, 0, worldWidth, worldHeight);
        }
    }

    private void spawnObstacle() {
        float height = _Constants.OBSTACLE_HEIGHT_MIN +
                (float)(Math.random() * (_Constants.OBSTACLE_HEIGHT_MAX - _Constants.OBSTACLE_HEIGHT_MIN));
        float x = _Constants.WORLD_WIDTH / _Constants.PPM + _Constants.SPAWN_DISTANCE;
        obstacles.add(new Obstacle(physicsWorld.getWorld(), x, height));
    }

    private void updateObstacles() {
        for (int i = obstacles.size - 1; i >= 0; i--) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update(Gdx.graphics.getDeltaTime());

            if (obstacle.isOffScreen()) {
                obstacle.destroy(physicsWorld.getWorld());
                obstacles.removeIndex(i);
            }
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Проверка столкновения игрока с землей
        if ((fixtureA.getUserData() == "player" && fixtureB.getUserData() == null) ||
                (fixtureB.getUserData() == "player" && fixtureA.getUserData() == null)) {
            player.setCanJump(true);
        }

        // Проверка столкновения с препятствием (для будущей логики Game Over)
        if ((fixtureA.getUserData() == "player" && fixtureB.getUserData() == "obstacle") ||
                (fixtureB.getUserData() == "player" && fixtureA.getUserData() == "obstacle")) {
            // Здесь будет логика Game Over
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Пустая реализация
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Пустая реализация
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Пустая реализация
    }

    @Override
    public void show() {
        // Вызывается при показе экрана
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / _Constants.PPM;
        camera.viewportHeight = height / _Constants.PPM;
        camera.update();
    }

    @Override
    public void pause() {
        // Для будущей реализации паузы
    }

    @Override
    public void resume() {
        // Для будущей реализации возобновления
    }

    @Override
    public void hide() {
        // Вызывается при скрытии экрана
    }

    @Override
    public void dispose() {
        batch.dispose();
        physicsWorld.dispose();
        for (Obstacle obstacle : obstacles) {
            obstacle.destroy(physicsWorld.getWorld());
        }
    }
}