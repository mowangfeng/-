package com.house.ui;

import com.house.model.House;
import com.house.service.HouseService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 编辑房屋对话框
 */
public class EditHouseDialog extends JDialog {
    private JTextField houseIdField;
    private JTextField ownerNameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField rentField;
    private JComboBox<String> statusCombo;
    private JButton saveButton;
    private JButton cancelButton;
    private HouseService houseService;
    private Runnable refreshCallback;

    public EditHouseDialog(JFrame parent, HouseService houseService, House house, Runnable refreshCallback) {
        super(parent, "编辑房屋", true);
        this.houseService = houseService;
        this.refreshCallback = refreshCallback;

        setSize(500, 450);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        // 房源ID（只读）
        JLabel idLabel = new JLabel("房源ID：");
        idLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        idLabel.setBounds(50, 30, 80, 25);
        panel.add(idLabel);

        houseIdField = new JTextField(String.valueOf(house.getHouseId()));
        houseIdField.setFont(new Font("宋体", Font.PLAIN, 12));
        houseIdField.setBounds(150, 30, 280, 25);
        houseIdField.setEnabled(false);
        panel.add(houseIdField);

        // 房主姓名
        JLabel ownerLabel = new JLabel("房主姓名：");
        ownerLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        ownerLabel.setBounds(50, 75, 80, 25);
        panel.add(ownerLabel);

        ownerNameField = new JTextField(house.getOwnerName());
        ownerNameField.setFont(new Font("宋体", Font.PLAIN, 12));
        ownerNameField.setBounds(150, 75, 280, 25);
        panel.add(ownerNameField);

        // 电话
        JLabel phoneLabel = new JLabel("电话：");
        phoneLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        phoneLabel.setBounds(50, 120, 80, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField(house.getPhone());
        phoneField.setFont(new Font("宋体", Font.PLAIN, 12));
        phoneField.setBounds(150, 120, 280, 25);
        panel.add(phoneField);

        // 地址
        JLabel addressLabel = new JLabel("地址：");
        addressLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        addressLabel.setBounds(50, 165, 80, 25);
        panel.add(addressLabel);

        addressField = new JTextField(house.getAddress());
        addressField.setFont(new Font("宋体", Font.PLAIN, 12));
        addressField.setBounds(150, 165, 280, 25);
        panel.add(addressField);

        // 月租金
        JLabel rentLabel = new JLabel("月租金：");
        rentLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        rentLabel.setBounds(50, 210, 80, 25);
        panel.add(rentLabel);

        rentField = new JTextField(String.valueOf(house.getMonthlyRent()));
        rentField.setFont(new Font("宋体", Font.PLAIN, 12));
        rentField.setBounds(150, 210, 280, 25);
        panel.add(rentField);

        // 房屋状态
        JLabel statusLabel = new JLabel("房屋状态：");
        statusLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        statusLabel.setBounds(50, 255, 80, 25);
        panel.add(statusLabel);

        statusCombo = new JComboBox<>(new String[]{"未出租", "已出租"});
        statusCombo.setFont(new Font("宋体", Font.PLAIN, 12));
        statusCombo.setBounds(150, 255, 280, 25);
        statusCombo.setSelectedItem(house.getStatus());
        panel.add(statusCombo);

        // 保存按钮
        saveButton = new JButton("保存");
        saveButton.setFont(new Font("宋体", Font.PLAIN, 12));
        saveButton.setBounds(150, 330, 80, 35);
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHouse();
            }
        });
        panel.add(saveButton);

        // 取消按钮
        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("宋体", Font.PLAIN, 12));
        cancelButton.setBounds(270, 330, 80, 35);
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

    private void saveHouse() {
        if (ownerNameField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty() ||
                addressField.getText().trim().isEmpty() ||
                rentField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int houseId = Integer.parseInt(houseIdField.getText());
            String ownerName = ownerNameField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            double monthlyRent = Double.parseDouble(rentField.getText().trim());
            String status = (String) statusCombo.getSelectedItem();

            House house = new House(houseId, ownerName, phone, address, monthlyRent, status);

            if (houseService.updateHouse(house)) {
                JOptionPane.showMessageDialog(this, "房屋修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                refreshCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "房屋修改失败", "失��", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "输入数据格式不正确", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
