package com.house.ui;

import com.house.service.HouseService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 删除房屋对话框
 */
public class DeleteHouseDialog extends JDialog {
    private JButton confirmButton;
    private JButton cancelButton;
    private HouseService houseService;
    private Runnable refreshCallback;
    private int houseId;

    public DeleteHouseDialog(JFrame parent, HouseService houseService, int houseId, Runnable refreshCallback) {
        super(parent, "删除房屋", true);
        this.houseService = houseService;
        this.refreshCallback = refreshCallback;
        this.houseId = houseId;

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));

        // 提示信息
        JLabel messageLabel = new JLabel("确定要删除房源ID为 " + houseId + " 的房屋吗？");
        messageLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        messageLabel.setBounds(40, 40, 320, 30);
        panel.add(messageLabel);

        JLabel warningLabel = new JLabel("此操作无法恢复！");
        warningLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(40, 80, 320, 25);
        panel.add(warningLabel);

        // 确认按钮
        confirmButton = new JButton("确认删除");
        confirmButton.setFont(new Font("宋体", Font.PLAIN, 12));
        confirmButton.setBounds(90, 130, 90, 35);
        confirmButton.setBackground(new Color(231, 76, 60));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteHouse();
            }
        });
        panel.add(confirmButton);

        // 取消按钮
        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("宋体", Font.PLAIN, 12));
        cancelButton.setBounds(220, 130, 90, 35);
        cancelButton.setBackground(new Color(149, 165, 166));
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

    private void deleteHouse() {
        if (houseService.deleteHouse(houseId)) {
            JOptionPane.showMessageDialog(this, "房屋删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            refreshCallback.run();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "房屋删除失败", "失败", JOptionPane.ERROR_MESSAGE);
        }
    }
}
