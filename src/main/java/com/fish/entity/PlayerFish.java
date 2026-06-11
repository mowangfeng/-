package com.fish.entity;

import com.fish.util.Constants;
import java.awt.*;

public class PlayerFish extends Fish {
    private int targetX;
    private int targetY;
    private int score = 0;
    private int level = 1;

    public PlayerFish(double x, double y) {
        super(x, y, Constants.PLAYER_INITIAL_SIZE, Constants.PLAYER_INITIAL_SPEED, 
              new Color(255, 200, 0), Constants.FishType.GOLDFISH);
        this.targetX = (int) x;
        this.targetY = (int) y;
    }

    @Override
    public void update() {
        // 向目标位置移动
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        } else {
            x = targetX;
            y = targetY;
        }
    }

    public void setTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public void addScore(int points) {
        score += points;
    }

    public boolean canEvolve() {
        return score >= Constants.EVOLUTION_THRESHOLD;
    }

    public void evolve() {
        if (!canEvolve()) return;

        // 体型增长 20%
        size = (int) (size * Constants.PLAYER_GROWTH_RATE);
        speed = Math.min(Constants.PLAYER_INITIAL_SPEED + level, 8);
        level++;
        score -= Constants.EVOLUTION_THRESHOLD;

        // 更换海洋生物类型
        Constants.FishType[] types = Constants.FishType.values();
        if (level - 1 < types.length) {
            type = types[level - 1];
            updateColorByType();
        }
    }

    private void updateColorByType() {
        switch (type) {
            case GOLDFISH:
                color = new Color(255, 200, 0);
                break;
            case CLOWNFISH:
                color = new Color(255, 100, 50);
                break;
            case BLUEFISH:
                color = new Color(0, 100, 255);
                break;
            case TUNA:
                color = new Color(100, 150, 200);
                break;
            case DOLPHIN:
                color = new Color(100, 200, 255);
                break;
            case WHALE:
                color = new Color(50, 100, 150);
                break;
            case SHARK:
                color = new Color(150, 150, 150);
                break;
            case GIANT_SQUID:
                color = new Color(150, 0, 150);
                break;
            case LEVIATHAN:
                color = new Color(30, 30, 100);
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!isAlive) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制鱼身
        g2d.setColor(color);
        g2d.fillOval((int)(x - size), (int)(y - size), size * 2, size * 2);
        
        // 绘制鱼眼
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)(x - size/3), (int)(y - size/4), size/3, size/3);
        
        // 绘制瞳孔
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x - size/4), (int)(y - size/6), size/6, size/6);
        
        // 绘制轮廓
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval((int)(x - size), (int)(y - size), size * 2, size * 2);
    }

    public int getScore() { return score; }
    public int getLevel() { return level; }
    public Constants.FishType getCurrentType() { return type; }
}
