package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Texture texture;
    private float currentX; // Текущая позиция первой картинки
    private float speed;

    public Background(String texturePath, float speed) {
        this.texture = new Texture(texturePath);
        this.speed = speed;
        this.currentX = 0; // Начинаем с левого края
    }

    public void update(float dt) {
        // Двигаем фон влево
        currentX -= speed * dt;

        // МАГИЯ БЕСШОВНОСТИ:
        // Как только картинка полностью уехала влево (на свою ширину),
        // мы мгновенно возвращаем её в начало (в 0).
        // Так как вторая картинка всегда рисуется справа от currentX, дырок не будет.
        if (currentX <= -texture.getWidth()) {
            currentX = 0;
        }
    }

    public void render(SpriteBatch batch, float yPosition) {
        // Рисуем первую копию
        batch.draw(texture, currentX, yPosition, texture.getWidth(), 480); // 480 - высота экрана

        // Рисуем вторую копию сразу справа от первой
        batch.draw(texture, currentX + texture.getWidth(), yPosition, texture.getWidth(), 480);
    }

    public void dispose() {
        texture.dispose();
    }
}