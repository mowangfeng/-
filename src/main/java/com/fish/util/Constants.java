package com.fish.util;

public class Constants {
    // 游戏窗口
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;
    public static final int FPS = 60;
    public static final int FRAME_DELAY = 1000 / FPS;

    // 游戏区域
    public static final int GAME_AREA_LEFT = 10;
    public static final int GAME_AREA_TOP = 80;
    public static final int GAME_AREA_WIDTH = WINDOW_WIDTH - 20;
    public static final int GAME_AREA_HEIGHT = WINDOW_HEIGHT - 90;

    // 玩家鱼
    public static final int PLAYER_INITIAL_SIZE = 20;
    public static final int PLAYER_INITIAL_SPEED = 5;
    public static final double PLAYER_GROWTH_RATE = 1.2; // 20% 增长

    // 敌方鱼
    public static final int ENEMY_MIN_SIZE = 10;
    public static final int ENEMY_MAX_SIZE = 35;
    public static final int ENEMY_MIN_SPEED = 1;
    public static final int ENEMY_MAX_SPEED = 4;
    public static final int INITIAL_ENEMY_COUNT = 10;

    // BOSS 鱼
    public static final int BOSS_MIN_SIZE_MULTIPLIER = 2; // BOSS 最小是玩家的 2 倍
    public static final int BOSS_MAX_SIZE_MULTIPLIER = 3; // BOSS 最大是玩家的 3 倍
    public static final int BOSS_SPAWN_PROBABILITY = 5; // 5% 概率生成 BOSS

    // 积分系统
    public static final int EVOLUTION_THRESHOLD = 100; // 进化积分阈值
    public static final int SMALL_FISH_SCORE = 10;
    public static final int MEDIUM_FISH_SCORE = 15;
    public static final int LARGE_FISH_SCORE = 20;
    public static final int BOSS_FISH_SCORE = 50;

    // 难度系统
    public static final int DIFFICULTY_INCREASE_INTERVAL = 60000; // 60 秒
    public static final double DIFFICULTY_MULTIPLIER = 1.1; // 每分钟增加 10% 难度
    public static final int REWARD_WAVE_INTERVAL = 60000; // 60 秒
    public static final int REWARD_WAVE_COUNT = 5; // 每波刷新 5 条鱼

    // 碰撞检测
    public static final double COLLISION_RADIUS_RATIO = 0.8; // 碰撞半径是鱼大小的 80%

    // 海洋生物类型
    public enum FishType {
        GOLDFISH("金鱼"),
        CLOWNFISH("小丑鱼"),
        BLUEFISH("蓝鱼"),
        TUNA("金枪鱼"),
        DOLPHIN("海豚"),
        WHALE("鲸鱼"),
        SHARK("鲨鱼"),
        GIANT_SQUID("巨型乌贼"),
        LEVIATHAN("利维坦");

        public final String displayName;

        FishType(String displayName) {
            this.displayName = displayName;
        }
    }
}
