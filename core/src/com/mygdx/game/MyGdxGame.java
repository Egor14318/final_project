package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {






	SpriteBatch batch;
	Background backgroundObj;
	World world;
	OrthographicCamera camera;

	Array<Entity> entities;
	Ball ball;

	float spawnTimer = 0;
	float spawnInterval = 2.0f;

	Texture background;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");


		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);
		backgroundObj = new Background("background.jpg", 150f);

		world = new World(new Vector2(0, -10f), true);
		// ИСПРАВЛЕНО: имя класса с маленькой буквы, как у тебя
		world.setContactListener(new contactListener());

		// Создание Земли
		BodyDef groundBodyDef = new BodyDef();
		Body groundBody = world.createBody(groundBodyDef);
		EdgeShape groundEdge = new EdgeShape();

		groundEdge.set(new Vector2(-200, 0.71f), new Vector2(10000, 0.71f));

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundEdge;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.1f;
		groundBody.createFixture(fixtureDef).setUserData("ground");
		groundEdge.dispose();

		entities = new Array<>();
		ball = new Ball(world, 100, 250);
		// ИСПРАВЛЕНО: добавляем объект ball, а не класс
		entities.add(ball);
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();

		// --- 1. ОБРАБОТКА ВВОДА ---
		// ИСПРАВЛЕНО: justTouched() вместо isTouched(), чтобы не прыгал постоянно
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
			// ИСПРАВЛЕНО: вызываем у экземпляра ball
			ball.jump();
		}


		// --- 2. ФИЗИКА ---
		world.step(1/60f, 6, 2);

		// --- 3. КАМЕРА ---
		// ИСПРАВЛЕНО: берем позицию у экземпляра ball
		camera.position.x = ball.getBody().getPosition().x * Entity.PPM;
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		// --- 4. СПАВН ПРЕПЯТСТВИЙ ---
		spawnTimer += dt;
		if (spawnTimer >= spawnInterval) {
			spawnTimer = 0;
			float spawnX = camera.position.x + 500; // Спавн справа от камеры

			// === НАСТРОЙКА ВЫСОТЫ ===
			float groundLevelY = 110f; // Высота земли в пикселях (та, которую ты задал в EdgeShape * PPM)
			float obstacleHeight = 128f; // Высота твоей картинки obstacle.png в пикселях

			// Центр препятствия = Уровень земли + половина высоты препятствия
			float spawnY = groundLevelY + (obstacleHeight / 2f);
			// ========================

			entities.add(new Obstacle(world, spawnX, spawnY));

			if (spawnInterval > 0.8f) spawnInterval -= 0.05f;
		}

		// --- 5. ОБНОВЛЕНИЕ И УДАЛЕНИЕ ---
		for (Entity entity : entities) {
			entity.update(dt);
		}

		for (int i = entities.size - 1; i >= 0; i--) {
			Entity entity = entities.get(i);
			if (entity.getSprite().getX() < camera.position.x - 500 || entity.isToRemove()) {
				entity.destroy();
				entities.removeIndex(i);
			}
		}

		// --- 6. ОТРИСОВКА ---
		ScreenUtils.clear(0.5f, 0.8f, 1f, 1);
		batch.begin();
		backgroundObj.update(dt);
		backgroundObj.render(batch, 0);

		float bgX = (camera.position.x * 0.5f) % background.getWidth();


		for (Entity entity : entities) {
			entity.render(batch);
		}

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		world.dispose();
		backgroundObj.dispose();
		for (Entity entity : entities) {
			entity.destroy();
		}
	}
}