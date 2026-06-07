package com.mygdx.game.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;


// todo import ru.innovationcampus.vsu26.lependin_e.happy_flappy_bird.components.PointCounter;
import com.mygdx.game.characters.Tube;
import com.mygdx.game.characters.Bird;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.MyGdxGame;



public class ScreenGame implements Screen {

    ScreenRestart screenRestart;

    Bird bird;

    MovingBackground background;

    int gamePoints;
    boolean isGameOver;
    MyGdxGame myGdxGame;
    Tube[] tubes;
    int tubeCount = 3;
    final int pointCounterMarginTop = 60;
    final int pointCounterMarginRight = 400;



    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        bird = new Bird(20, MyGdxGame.SCR_HEIGHT/2, 7, 240, 160);
        background = new MovingBackground("background.jpg");
        initTubes();
    }


    @Override
    public void show(){
        isGameOver = false;
        gamePoints = 0;
        bird.setY(MyGdxGame.SCR_HEIGHT/2);
        initTubes();

    }


    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            System.out.println("Just touched");
            bird.onClick();
        }

        background.move();
        bird.fly();
        if (!bird.isInField()) {
            System.out.println("not in field");
            isGameOver = true;
        }
        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        for (Tube tube : tubes) {
            tube.move();
            if (tube.isHit(bird)) {
                System.out.println("hit");
                isGameOver = true;
            } else if (tube.needAddPoint(bird)) {
                gamePoints +=1;
                tube.setPointReceived();
                System.out.println(gamePoints);
            }
        }
        if (isGameOver) {
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }
        if (isGameOver) { myGdxGame.screenRestart.gamePoints = gamePoints; myGdxGame.setScreen(myGdxGame.screenRestart); }
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        bird.draw(myGdxGame.batch);

        for (Tube tube : tubes) tube.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }


    public void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i);
        }
    }


    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void hide() {
    }


    @Override
    public void dispose() {
        bird.dispose();
        background.dispose();
    }

}

