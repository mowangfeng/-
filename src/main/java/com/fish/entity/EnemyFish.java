package com.fish.entity;

import com.fish.util.Constants;
import java.awt.*;

public class EnemyFish extends Fish {
    private double directionX;
    private double directionY;
    private int changeDirectionCounter = 0;
    private static final int CHANGE_DIRECTION_INTERVAL = 60; // 每 60 帧改变方向

    public EnemyFish(double x, double y, int size, int speed) {
        super(x, y, size, speed, getColorBySize(size), Constants.FishType.CLOWNFISH);
        randomizeDirection();
    }

    @Override
    public void update() {
        // 移动
        x += directionX * speed;
        y += directionY * speed;

        // 定期改变方向
        changeDirectionCounter++;
        if (changeDirectionCounter > CHANGE_DIRECTION_INTERVAL) {
            randomizeDirection();
            changeDirectionCounter = 0;
        }

        // 边界反弹
        if (x - size < Constants.GAME_AREA_LEFT || x + size > Constants.GAME_AREA_LEFT + Constants.GAME_AREA_WIDTH) {
            directionX = -directionX;
        }
        if (y - size < Constants.GAME_AREA_TOP || y + size > Constants.GAME_AREA_TOP + Constants.GAME_AREA_HEIGHT) {
            directionY = -directionY;
        }
    }

    private void randomizeDirection() {
        double angle = random.nextDouble() * 2 * Math.PI;
        directionX = Math.cos(angle);
        directionY = Math.sin(angle);
    }

    private static Color getColorBySize(int size) {
        if (size < 15) {
            return new Color(100, 200, 100); // 绿色小鱼
        } else if (size < 25) {
            return new Color(100, 150, 255); // 蓝色中等鱼
        } else {
            return new Color(255, 100, 100); // 红色大鱼
        }
    }
}
