package com.fish.game;

import com.fish.entity.*;
import com.fish.listener.GameKeyListener;
import com.fish.util.CollisionDetector;
import com.fish.util.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class GamePanel extends JPanel {
    private PlayerFish playerFish;
    private List<EnemyFish> enemyFishes = new ArrayList<>();
    private List<BossFish> bossFishes = new ArrayList<>();
    private GameKeyListener keyListener;
    private GameController gameController;

    private long gameStartTime;
    private long lastDifficultyIncreaseTime;
    private long lastRewardWaveTime;
    private int gameTime = 0; // 游戏进行时间（秒）
    private double difficultyMultiplier = 1.0;
    private int currentEnemyCount;

    public GamePanel(GameController gameController) {
        this.gameController = gameController;
        setBackground(new Color(30, 144, 255)); // 深蓝色海洋背景
        setFocusable(true);

        // 初始化玩家鱼
        playerFish = new PlayerFish(
            Constants.GAME_AREA_LEFT + Constants.GAME_AREA_WIDTH / 2,
            Constants.GAME_AREA_TOP + Constants.GAME_AREA_HEIGHT / 2
        );

        // 初始化敌方鱼
        currentEnemyCount = Constants.INITIAL_ENEMY_COUNT;
        spawnEnemyFishes(currentEnemyCount);

        // 初始化键盘监听
        keyListener = new GameKeyListener(this);
        addKeyListener(keyListener);

        gameStartTime = System.currentTimeMillis();
        lastDifficultyIncreaseTime = gameStartTime;
        lastRewardWaveTime = gameStartTime;
    }

    public void update() {
        if (!gameController.isGameRunning()) return;

        // 更新游戏时间
        long currentTime = System.currentTimeMillis();
        gameTime = (int) ((currentTime - gameStartTime) / 1000);

        // 处理玩家输入
        handleInput();

        // 更新玩家鱼
        playerFish.update();
        CollisionDetector.keepInBounds(playerFish);

        // 更新敌方鱼
        for (EnemyFish enemy : enemyFishes) {
            if (enemy.isAlive()) {
                enemy.update();
                CollisionDetector.keepInBounds(enemy);
            }
        }

        // 更新 BOSS 鱼
        for (BossFish boss : bossFishes) {
            if (boss.isAlive()) {
                boss.update();
                CollisionDetector.keepInBounds(boss);
            }
        }

        // 检测碰撞
        checkCollisions();

        // 难度递增
        checkDifficultyIncrease(currentTime);

        // 奖励波刷新
        checkRewardWave(currentTime);

        // 检查玩家进化
        if (playerFish.canEvolve()) {
            playerFish.evolve();
        }

        // 清理死亡的鱼
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
        // 检测与敌方鱼的碰撞
        for (EnemyFish enemy : new ArrayList<>(enemyFishes)) {
            if (!enemy.isAlive() || !playerFish.isAlive()) continue;

            if (CollisionDetector.isColliding(playerFish, enemy)) {
                if (playerFish.getSize() > enemy.getSize()) {
                    // 玩家吞噬敌方鱼
                    enemy.setAlive(false);
                    int score = getScoreByFishSize(enemy.getSize());
                    playerFish.addScore(score);
                } else if (enemy.getSize() > playerFish.getSize()) {
                    // 玩家被吃掉
                    playerFish.setAlive(false);
                    gameController.gameOver();
                }
            }
        }

        // 检测与 BOSS 鱼的碰撞
        for (BossFish boss : new ArrayList<>(bossFishes)) {
            if (!boss.isAlive() || !playerFish.isAlive()) continue;

            if (CollisionDetector.isColliding(playerFish, boss)) {
                if (playerFish.getSize() > boss.getSize()) {
                    // 玩家吞噬 BOSS
                    boss.takeDamage();
                    if (!boss.isAlive()) {
                        playerFish.addScore(Constants.BOSS_FISH_SCORE);
                    }
                } else {
                    // 玩家被 BOSS 吃掉
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

            // 增加敌方鱼的数量和速度
            int newEnemyCount = (int) (currentEnemyCount * Constants.DIFFICULTY_MULTIPLIER);
            spawnEnemyFishes(newEnemyCount - currentEnemyCount);
            currentEnemyCount = newEnemyCount;
        }
    }

    private void checkRewardWave(long currentTime) {
        if (currentTime - lastRewardWaveTime > Constants.REWARD_WAVE_INTERVAL) {
            // 刷新奖励波
            spawnEnemyFishes(Constants.REWARD_WAVE_COUNT);
            lastRewardWaveTime = currentTime;
        }
    }

    private void spawnEnemyFishes(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int x = Constants.GAME_AREA_LEFT + random.nextInt(Constants.GAME_AREA_WIDTH);
            int y = Constants.GAME_AREA_TOP + random.nextInt(Constants.GAME_AREA_HEIGHT);
            int size = Constants.ENEMY_MIN_SIZE + random.nextInt(Constants.ENEMY_MAX_SIZE - Constants.ENEMY_MIN_SIZE + 1);
            int speed = Constants.ENEMY_MIN_SPEED + random.nextInt(Constants.ENEMY_MAX_SPEED - Constants.ENEMY_MIN_SPEED + 1);
            
            enemyFishes.add(new EnemyFish(x, y, size, speed));

            // 随机生成 BOSS 鱼
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

        // 绘制游戏区域边框
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(Constants.GAME_AREA_LEFT, Constants.GAME_AREA_TOP, 
                     Constants.GAME_AREA_WIDTH, Constants.GAME_AREA_HEIGHT);

        // 绘制所有鱼
        playerFish.draw(g2d);
        for (EnemyFish enemy : enemyFishes) {
            enemy.draw(g2d);
        }
        for (BossFish boss : bossFishes) {
            boss.draw(g2d);
        }

        // 绘制 UI 信息
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
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;
            g.drawString(text, x, y);
        }
    }

    public PlayerFish getPlayerFish() {
        return playerFish;
    }
}
