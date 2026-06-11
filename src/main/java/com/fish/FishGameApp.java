package com.fish;

import com.fish.game.GameWindow;
import javax.swing.*;

public class FishGameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
