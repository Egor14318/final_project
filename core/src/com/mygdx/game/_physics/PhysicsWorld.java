package com.mygdx.game._physics;




import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game._utils._Constants;

    public class PhysicsWorld {
        private World world;
        private Box2DDebugRenderer debugRenderer;

        public PhysicsWorld() {
            world = new World(_Constants.GRAVITY, true);
            debugRenderer = new Box2DDebugRenderer();
            createGround();
        }

        private void createGround() {
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(0, 0);
            Body ground = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(_Constants.WORLD_WIDTH / _Constants.PPM,
                    _Constants.GROUND_HEIGHT / _Constants.PPM,
                    new com.badlogic.gdx.math.Vector2(
                            _Constants.WORLD_WIDTH / (2 * _Constants.PPM),
                            _Constants.GROUND_HEIGHT / (2 * _Constants.PPM)
                    ), 0);

            ground.createFixture(shape, 0);
            shape.dispose();
        }

        public void update(float delta) {
            world.step(_Constants.TIME_STEP,
                    _Constants.VELOCITY_ITERATIONS,
                    _Constants.POSITION_ITERATIONS);
        }

        public void render() {
            debugRenderer.render(world, getCamera().combined);
        }

        public World getWorld() {
            return world;
        }

        private com.badlogic.gdx.graphics.OrthographicCamera getCamera() {
            // Временный метод, в GameScreen будет своя камера
            return new com.badlogic.gdx.graphics.OrthographicCamera();
        }

        public void dispose() {
            world.dispose();
            debugRenderer.dispose();
        }
    }