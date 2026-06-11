package com.house.ui;

import com.house.model.House;
import com.house.service.HouseService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 添加房屋对话框
 */
public class AddHouseDialog extends JDialog {
    private JTextField ownerNameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField rentField;
    private JComboBox<String> statusCombo;
    private JButton addButton;
    private JButton cancelButton;
    private HouseService houseService;
    private Runnable refreshCallback;

    public AddHouseDialog(JFrame parent, HouseService houseService, Runnable refreshCallback) {
        super(parent, "新增房屋", true);
        this.houseService = houseService;
        this.refreshCallback = refreshCallback;

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        // 房主姓名
        JLabel ownerLabel = new JLabel("房主姓名：");
        ownerLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        ownerLabel.setBounds(50, 30, 80, 25);
        panel.add(ownerLabel);

        ownerNameField = new JTextField();
        ownerNameField.setFont(new Font("宋体", Font.PLAIN, 12));
        ownerNameField.setBounds(150, 30, 280, 25);
        panel.add(ownerNameField);

        // 电话
        JLabel phoneLabel = new JLabel("电话：");
        phoneLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        phoneLabel.setBounds(50, 75, 80, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("宋体", Font.PLAIN, 12));
        phoneField.setBounds(150, 75, 280, 25);
        panel.add(phoneField);

        // 地址
        JLabel addressLabel = new JLabel("地址：");
        addressLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        addressLabel.setBounds(50, 120, 80, 25);
        panel.add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(new Font("宋体", Font.PLAIN, 12));
        addressField.setBounds(150, 120, 280, 25);
        panel.add(addressField);

        // 月租金
        JLabel rentLabel = new JLabel("月租金：");
        rentLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        rentLabel.setBounds(50, 165, 80, 25);
        panel.add(rentLabel);

        rentField = new JTextField();
        rentField.setFont(new Font("宋体", Font.PLAIN, 12));
        rentField.setBounds(150, 165, 280, 25);
        panel.add(rentField);

        // 房屋状态
        JLabel statusLabel = new JLabel("房屋状态：");
        statusLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        statusLabel.setBounds(50, 210, 80, 25);
        panel.add(statusLabel);

        statusCombo = new JComboBox<>(new String[]{"未出租", "已出租"});
        statusCombo.setFont(new Font("宋体", Font.PLAIN, 12));
        statusCombo.setBounds(150, 210, 280, 25);
        panel.add(statusCombo);

        // 添加按钮
        addButton = new JButton("添加");
        addButton.setFont(new Font("宋体", Font.PLAIN, 12));
        addButton.setBounds(150, 280, 80, 35);
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHouse();
            }
        });
        panel.add(addButton);

        // 取消按钮
        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("宋体", Font.PLAIN, 12));
        cancelButton.setBounds(270, 280, 80, 35);
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    private void addHouse() {
        // 验证输入
        if (ownerNameField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty() ||
                addressField.getText().trim().isEmpty() ||
                rentField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能���空", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int houseId = houseService.getMaxHouseId() + 1;
            String ownerName = ownerNameField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            double monthlyRent = Double.parseDouble(rentField.getText().trim());
            String status = (String) statusCombo.getSelectedItem();

            House house = new House(houseId, ownerName, phone, address, monthlyRent, status);

            if (houseService.addHouse(house)) {
                JOptionPane.showMessageDialog(this, "房屋添加成功！房源ID: " + houseId, "成功", JOptionPane.INFORMATION_MESSAGE);
                refreshCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "房屋添加失败", "失败", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "月租金必须是数字", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
