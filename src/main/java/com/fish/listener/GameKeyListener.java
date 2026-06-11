package com.fish.listener;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private final JPanel gamePanel;
    private boolean[] keysPressed = new boolean[256];

    public GameKeyListener(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isKeyPressed(int keyCode) {
        return keyCode < keysPressed.length && keysPressed[keyCode];
    }
}
