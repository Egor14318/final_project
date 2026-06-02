package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	World world;
	BodyDef groundBodyDef;
	Body groundBody;
	FixtureDef fixtureDef;
	EdgeShape groundEdge;
	Texture background;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		world = new World(new Vector2(0, -10f), true);
		groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, 0));
		fixtureDef = new FixtureDef();
		groundEdge = new EdgeShape();
		fixtureDef.shape = groundEdge;
		groundBody = world.createBody(groundBodyDef);
		fixtureDef.friction = 0.5f;   // Трение
		fixtureDef.restitution = 0.1f;
		groundBody.createFixture(fixtureDef);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(background,0,0);

		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		groundEdge.dispose();
		world.dispose();
	}
}
