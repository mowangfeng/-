package com.house;

import com.house.ui.LoginFrame;
import javax.swing.*;

/**
 * 房屋出租管理系统启动类
 */
public class HouseRentalApp {
    public static void main(String[] args) {
        // 设置Swing外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 显示登录窗口
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
