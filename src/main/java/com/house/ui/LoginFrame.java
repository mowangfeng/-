package com.house.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录界面
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;

    public LoginFrame() {
        setTitle("房屋出租管理系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // 创建面板
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制渐变背景
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(52, 152, 219),
                        0, getHeight(), new Color(155, 89, 182));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // 标题
        JLabel titleLabel = new JLabel("房屋出租管理系统");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(150, 30, 300, 50);
        panel.add(titleLabel);

        // 用户名标签
        JLabel usernameLabel = new JLabel("用户名：");
        usernameLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(120, 120, 80, 30);
        panel.add(usernameLabel);

        // 用户名输入框
        usernameField = new JTextField();
        usernameField.setFont(new Font("宋体", Font.PLAIN, 14));
        usernameField.setBounds(210, 120, 270, 30);
        panel.add(usernameField);

        // 密码标签
        JLabel passwordLabel = new JLabel("密  码：");
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(120, 170, 80, 30);
        panel.add(passwordLabel);

        // 密码输入框
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("宋体", Font.PLAIN, 14));
        passwordField.setBounds(210, 170, 270, 30);
        panel.add(passwordField);

        // 登录按钮
        loginButton = new JButton("登录");
        loginButton.setFont(new Font("宋体", Font.PLAIN, 14));
        loginButton.setBounds(180, 250, 80, 40);
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(loginButton);

        // 退出按钮
        exitButton = new JButton("退出");
        exitButton.setFont(new Font("宋体", Font.PLAIN, 14));
        exitButton.setBounds(340, 250, 80, 40);
        exitButton.setBackground(new Color(231, 76, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(exitButton);

        // 事件处理
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // 回车键登录
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // 简单的用户验证（实际项目中应使用数据库）
        if ("admin".equals(username) && "admin".equals(password)) {
            // 登录成功，打开主窗口
            new MainFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }
}
