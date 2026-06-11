package com.fish.game;

import javax.swing.*;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private GameController gameController;

    public GameWindow() {
        setTitle("🐟 吃鱼游戏 (Fish Eater Game)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(com.fish.util.Constants.WINDOW_WIDTH, com.fish.util.Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        gameController = new GameController(null);
        gamePanel = new GamePanel(gameController);
        gameController = new GameController(gamePanel);

        add(gamePanel);
        setVisible(true);

        // 启动游戏循环
        startGameLoop();
    }

    private void startGameLoop() {
        new Thread(() -> {
            gameController.startGame();
            long lastTime = System.nanoTime();

            while (true) {
                long currentTime = System.nanoTime();
                long deltaTime = currentTime - lastTime;
                lastTime = currentTime;

                // 检查重新开始
                if (gameController.shouldRestart()) {
                    gameController.resetRestart();
                    gameController.startGame();
                    gamePanel = new GamePanel(gameController);
                    GameWindow.this.getContentPane().removeAll();
                    GameWindow.this.add(gamePanel);
                    GameWindow.this.revalidate();
                    GameWindow.this.repaint();
                }

                // 更新游戏
                gamePanel.update();
                gamePanel.repaint();

                // 控制帧率
                try {
                    Thread.sleep(com.fish.util.Constants.FRAME_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
