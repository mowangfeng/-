package com.fish.util;

import com.fish.entity.Fish;

public class CollisionDetector {
    /**
     * 检测两条鱼是否发生碰撞
     */
    public static boolean isColliding(Fish fish1, Fish fish2) {
        double dx = fish1.getX() - fish2.getX();
        double dy = fish1.getY() - fish2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        double radius1 = fish1.getSize() * Constants.COLLISION_RADIUS_RATIO;
        double radius2 = fish2.getSize() * Constants.COLLISION_RADIUS_RATIO;
        
        return distance < (radius1 + radius2);
    }

    /**
     * 检测鱼是否出界
     */
    public static boolean isOutOfBounds(Fish fish) {
        return fish.getX() - fish.getSize() < Constants.GAME_AREA_LEFT ||
               fish.getX() + fish.getSize() > Constants.GAME_AREA_LEFT + Constants.GAME_AREA_WIDTH ||
               fish.getY() - fish.getSize() < Constants.GAME_AREA_TOP ||
               fish.getY() + fish.getSize() > Constants.GAME_AREA_TOP + Constants.GAME_AREA_HEIGHT;
    }

    /**
     * 保持鱼在游戏区域内
     */
    public static void keepInBounds(Fish fish) {
        int minX = Constants.GAME_AREA_LEFT + fish.getSize();
        int maxX = Constants.GAME_AREA_LEFT + Constants.GAME_AREA_WIDTH - fish.getSize();
        int minY = Constants.GAME_AREA_TOP + fish.getSize();
        int maxY = Constants.GAME_AREA_TOP + Constants.GAME_AREA_HEIGHT - fish.getSize();

        if (fish.getX() < minX) fish.setX(minX);
        if (fish.getX() > maxX) fish.setX(maxX);
        if (fish.getY() < minY) fish.setY(minY);
        if (fish.getY() > maxY) fish.setY(maxY);
    }
}
