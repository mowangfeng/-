package com.fish.game;

import com.fish.entity.*;
import com.fish.listener.GameKeyListener;
import com.fish.util.CollisionDetector;
import com.fish.util.Constants;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class GamePanel extends JPanel {
    private PlayerFish playerFish;
    private java.util.List<EnemyFish> enemyFishes = new ArrayList<>();
    private java.util.List<BossFish> bossFishes = new ArrayList<>();
    private GameKeyListener keyListener;
    private GameController gameController;

    private long gameStartTime;
    private long lastDifficultyIncreaseTime;
    private long lastRewardWaveTime;
    private int gameTime = 0;
    private double difficultyMultiplier = 1.0;
    private int currentEnemyCount;

    public GamePanel(GameController gameController) {
        this.gameController = gameController;
        setBackground(new Color(30, 144, 255));
        setFocusable(true);

        playerFish = new PlayerFish(
            Constants.GAME_AREA_LEFT + Constants.GAME_AREA_WIDTH / 2,
            Constants.GAME_AREA_TOP + Constants.GAME_AREA_HEIGHT / 2
        );

        currentEnemyCount = Constants.INITIAL_ENEMY_COUNT;
        spawnEnemyFishes(currentEnemyCount);

        keyListener = new GameKeyListener(this);
        addKeyListener(keyListener);

        gameStartTime = System.currentTimeMillis();
        lastDifficultyIncreaseTime = gameStartTime;
        lastRewardWaveTime = gameStartTime;
    }

    public void update() {
        if (!gameController.isGameRunning()) return;

        long currentTime = System.currentTimeMillis();
        gameTime = (int) ((currentTime - gameStartTime) / 1000);

        handleInput();

        playerFish.update();
        CollisionDetector.keepInBounds(playerFish);

        for (EnemyFish enemy : enemyFishes) {
            if (enemy.isAlive()) {
                enemy.update();
                CollisionDetector.keepInBounds(enemy);
            }
        }

        for (BossFish boss : bossFishes) {
            if (boss.isAlive()) {
                boss.update();
                CollisionDetector.keepInBounds(boss);
            }
        }

        checkCollisions();
        checkDifficultyIncrease(currentTime);
        checkRewardWave(currentTime);

        if (playerFish.canEvolve()) {
            playerFish.evolve();
        }

        enemyFishes.removeIf(fish -> !fish.isAlive());
        bossFishes.removeIf(fish -> !fish.isAlive());
    }

    private void handleInput() {
        int playerSpeed = playerFish.getSpeed();
        int step = playerSpeed;

        if (keyListener.isKeyPressed(KeyEvent.VK_W)) {
            playerFish.setTarget((int) playerFish.getX(), (int) (playerFish.getY() - step));
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_A)) {
            playerFish.setTarget((int) (playerFish.getX() - step), (int) playerFish.getY());
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_S)) {
            playerFish.setTarget((int) playerFish.getX(), (int) (playerFish.getY() + step));
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_D)) {
            playerFish.setTarget((int) (playerFish.getX() + step), (int) playerFish.getY());
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            gameController.togglePause();
            try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_R)) {
            gameController.restartGame();
        }
    }

    private void checkCollisions() {
        for (EnemyFish enemy : new ArrayList<>(enemyFishes)) {
            if (!enemy.isAlive() || !playerFish.isAlive()) continue;

            if (CollisionDetector.isColliding(playerFish, enemy)) {
                if (playerFish.getSize() > enemy.getSize()) {
                    enemy.setAlive(false);
                    int score = getScoreByFishSize(enemy.getSize());
                    playerFish.addScore(score);
                } else if (enemy.getSize() > playerFish.getSize()) {
                    playerFish.setAlive(false);
                    gameController.gameOver();
                }
            }
        }

        for (BossFish boss : new ArrayList<>(bossFishes)) {
            if (!boss.isAlive() || !playerFish.isAlive()) continue;

            if (CollisionDetector.isColliding(playerFish, boss)) {
                if (playerFish.getSize() > boss.getSize()) {
                    boss.takeDamage();
                    if (!boss.isAlive()) {
                        playerFish.addScore(Constants.BOSS_FISH_SCORE);
                    }
                } else {
                    playerFish.setAlive(false);
                    gameController.gameOver();
                }
            }
        }
    }

    private void checkDifficultyIncrease(long currentTime) {
        if (currentTime - lastDifficultyIncreaseTime > Constants.DIFFICULTY_INCREASE_INTERVAL) {
            difficultyMultiplier *= Constants.DIFFICULTY_MULTIPLIER;
            lastDifficultyIncreaseTime = currentTime;

            int newEnemyCount = (int) (currentEnemyCount * Constants.DIFFICULTY_MULTIPLIER);
            spawnEnemyFishes(newEnemyCount - currentEnemyCount);
            currentEnemyCount = newEnemyCount;
        }
    }

    private void checkRewardWave(long currentTime) {
        if (currentTime - lastRewardWaveTime > Constants.REWARD_WAVE_INTERVAL) {
            spawnEnemyFishes(Constants.REWARD_WAVE_COUNT);
            lastRewardWaveTime = currentTime;
        }
    }

    private void spawnEnemyFishes(int count) {
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < count; i++) {
            int x = Constants.GAME_AREA_LEFT + random.nextInt(Constants.GAME_AREA_WIDTH);
            int y = Constants.GAME_AREA_TOP + random.nextInt(Constants.GAME_AREA_HEIGHT);
            int size = Constants.ENEMY_MIN_SIZE + random.nextInt(Constants.ENEMY_MAX_SIZE - Constants.ENEMY_MIN_SIZE + 1);
            int speed = Constants.ENEMY_MIN_SPEED + random.nextInt(Constants.ENEMY_MAX_SPEED - Constants.ENEMY_MIN_SPEED + 1);
            
            enemyFishes.add(new EnemyFish(x, y, size, speed));

            if (random.nextInt(100) < Constants.BOSS_SPAWN_PROBABILITY) {
                bossFishes.add(new BossFish(x, y, playerFish.getSize()));
            }
        }
    }

    private int getScoreByFishSize(int size) {
        if (size < 15) return Constants.SMALL_FISH_SCORE;
        if (size < 25) return Constants.MEDIUM_FISH_SCORE;
        return Constants.LARGE_FISH_SCORE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(Constants.GAME_AREA_LEFT, Constants.GAME_AREA_TOP, 
                     Constants.GAME_AREA_WIDTH, Constants.GAME_AREA_HEIGHT);

        playerFish.draw(g2d);
        for (EnemyFish enemy : enemyFishes) {
            enemy.draw(g2d);
        }
        for (BossFish boss : bossFishes) {
            boss.draw(g2d);
        }

        drawUI(g2d);
    }

    private void drawUI(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.WHITE);

        g.drawString("Score: " + playerFish.getScore(), 20, 30);
        g.drawString("Level: " + playerFish.getLevel() + " (" + playerFish.getCurrentType().displayName + ")", 20, 55);
        g.drawString("Time: " + gameTime + "s | Enemies: " + enemyFishes.size() + " | Boss: " + bossFishes.size(), 400, 30);
        g.drawString("Controls: WASD - Move | SPACE - Pause | R - Restart | ESC - Quit", 400, 55);

        if (!gameController.isGameRunning()) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(new Color(255, 0, 0, 200));
            String text = gameController.isGameOver() ? "GAME OVER" : "PAUSED";
            java.awt.FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;
            g.drawString(text, x, y);
        }
    }

    public PlayerFish getPlayerFish() {
        return playerFish;
    }
}
