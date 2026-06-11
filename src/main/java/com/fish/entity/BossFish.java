package com.fish.entity;

import com.fish.util.Constants;
import java.awt.*;

public class BossFish extends Fish {
    private double directionX;
    private double directionY;
    private int changeDirectionCounter = 0;
    private static final int CHANGE_DIRECTION_INTERVAL = 30; // BOSS 更频繁地改变方向
    private int health; // BOSS 可能需要多次击中

    public BossFish(double x, double y, int playerSize) {
        // BOSS 的大小是玩家的 2-3 倍
        int bossSize = playerSize * (Constants.BOSS_MIN_SIZE_MULTIPLIER + 
                       new java.util.Random().nextInt(Constants.BOSS_MAX_SIZE_MULTIPLIER - Constants.BOSS_MIN_SIZE_MULTIPLIER + 1));
        
        super(x, y, bossSize, 3, new Color(200, 0, 0), Constants.FishType.SHARK);
        this.health = 3; // BOSS 生命值
        randomizeDirection();
    }

    @Override
    public void update() {
        // 移动
        x += directionX * speed;
        y += directionY * speed;

        // BOSS 更频繁地改变方向
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

    @Override
    public void draw(Graphics g) {
        if (!isAlive) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制 BOSS 鱼身（深红色）
        g2d.setColor(new Color(200, 0, 0));
        g2d.fillOval((int)(x - size), (int)(y - size), size * 2, size * 2);
        
        // 绘制鱼眼（白色）
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)(x - size/3), (int)(y - size/4), size/2, size/2);
        
        // 绘制瞳孔（黑色）
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x - size/4), (int)(y - size/6), size/3, size/3);
        
        // 绘制浓重的轮廓
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval((int)(x - size), (int)(y - size), size * 2, size * 2);
        
        // 绘制警告三角形（表示 BOSS）
        drawBossWarning(g2d);
    }

    private void drawBossWarning(Graphics2D g2d) {
        g2d.setColor(new Color(255, 200, 0));
        g2d.fillPolygon(
            new int[]{(int)x, (int)(x + size/2), (int)(x - size/2)},
            new int[]{(int)(y - size - 10), (int)(y - size + 5), (int)(y - size + 5)},
            3
        );
    }

    public void takeDamage() {
        health--;
        if (health <= 0) {
            isAlive = false;
        }
    }

    public boolean isAliveAndHealthy() {
        return isAlive && health > 0;
    }
}
