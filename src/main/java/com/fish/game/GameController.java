package com.fish.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private GamePanel gamePanel;
    private boolean isRunning = false;
    private boolean isPaused = false;
    private boolean isGameOver = false;
    private boolean shouldRestart = false;

    public GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.addKeyListener(this);
    }

    public void startGame() {
        isRunning = true;
        isPaused = false;
        isGameOver = false;
        shouldRestart = false;
    }

    public void togglePause() {
        if (isRunning && !isGameOver) {
            isPaused = !isPaused;
        }
    }

    public void gameOver() {
        isRunning = false;
        isGameOver = true;
    }

    public void restartGame() {
        shouldRestart = true;
    }

    public boolean isGameRunning() {
        return isRunning && !isPaused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean shouldRestart() {
        return shouldRestart;
    }

    public void resetRestart() {
        shouldRestart = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
