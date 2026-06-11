package com.fish.entity;

import com.fish.util.Constants;
import java.awt.*;
import java.util.Random;

public class Fish {
    protected double x;
    protected double y;
    protected int size;
    protected int speed;
    protected Color color;
    protected Constants.FishType type;
    protected boolean isAlive = true;

    protected static Random random = new Random();

    public Fish(double x, double y, int size, int speed, Color color, Constants.FishType type) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.color = color;
        this.type = type;
    }

    public void update() {
        // 基础更新逻辑由子类实现
    }

    public void draw(Graphics g) {
        if (!isAlive) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillOval((int)(x - size), (int)(y - size), size * 2, size * 2);
        
        // 绘制鱼的轮廓
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval((int)(x - size), (int)(y - size), size * 2, size * 2);
    }

    // Getters and Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public Constants.FishType getType() { return type; }
    public void setType(Constants.FishType type) { this.type = type; }

    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { isAlive = alive; }
}
