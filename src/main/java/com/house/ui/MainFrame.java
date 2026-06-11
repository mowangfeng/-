package com.house.ui;

import com.house.model.House;
import com.house.service.HouseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 主界面
 */
public class MainFrame extends JFrame {
    private HouseService houseService;
    private JTable houseTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("房屋出租管理系统 - 首页");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        houseService = new HouseService();

        // 创建菜单栏
        createMenuBar();

        // 创建工具栏
        createToolBar();

        // 创建表格
        createTable();

        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 房屋管理菜单
        JMenu houseMenu = new JMenu("房屋管理");
        houseMenu.setFont(new Font("宋体", Font.PLAIN, 12));

        JMenuItem addItem = new JMenuItem("新增房屋");
        addItem.addActionListener(e -> showAddHouseDialog());
        houseMenu.add(addItem);

        JMenuItem findItem = new JMenuItem("查找房屋");
        findItem.addActionListener(e -> showFindHouseDialog());
        houseMenu.add(findItem);

        JMenuItem editItem = new JMenuItem("修改房屋信息");
        editItem.addActionListener(e -> showEditHouseDialog());
        houseMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("删除房屋");
        deleteItem.addActionListener(e -> showDeleteHouseDialog());
        houseMenu.add(deleteItem);

        houseMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));
        houseMenu.add(exitItem);

        // 查看菜单
        JMenu viewMenu = new JMenu("查看");
        viewMenu.setFont(new Font("宋体", Font.PLAIN, 12));

        JMenuItem allHousesItem = new JMenuItem("所有房屋");
        allHousesItem.addActionListener(e -> refreshAllHouses());
        viewMenu.add(allHousesItem);

        JMenuItem availableItem = new JMenuItem("未出租房屋列表");
        availableItem.addActionListener(e -> showAvailableHouses());
        viewMenu.add(availableItem);

        menuBar.add(houseMenu);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton addBtn = new JButton("新增");
        addBtn.addActionListener(e -> showAddHouseDialog());
        toolBar.add(addBtn);

        toolBar.addSeparator();

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> refreshAllHouses());
        toolBar.add(refreshBtn);

        toolBar.addSeparator();

        JButton availableBtn = new JButton("未出租");
        availableBtn.addActionListener(e -> showAvailableHouses());
        toolBar.add(availableBtn);

        toolBar.addSeparator();

        JButton deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(e -> showDeleteHouseDialog());
        toolBar.add(deleteBtn);

        add(toolBar, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columnNames = {"房源ID", "房主姓名", "电话", "地址", "月租金", "房屋状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        houseTable = new JTable(tableModel);
        houseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        houseTable.setFont(new Font("宋体", Font.PLAIN, 12));
        houseTable.getTableHeader().setFont(new Font("宋体", Font.BOLD, 12));
        houseTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(houseTable);
        add(scrollPane, BorderLayout.CENTER);

        // 加载初始数据
        refreshAllHouses();
    }

    private void refreshAllHouses() {
        tableModel.setRowCount(0);
        List<House> houses = houseService.getAllHouses();
        for (House house : houses) {
            Object[] row = {
                    house.getHouseId(),
                    house.getOwnerName(),
                    house.getPhone(),
                    house.getAddress(),
                    house.getMonthlyRent(),
                    house.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void showAvailableHouses() {
        tableModel.setRowCount(0);
        List<House> houses = houseService.getAvailableHouses();
        for (House house : houses) {
            Object[] row = {
                    house.getHouseId(),
                    house.getOwnerName(),
                    house.getPhone(),
                    house.getAddress(),
                    house.getMonthlyRent(),
                    house.getStatus()
            };
            tableModel.addRow(row);
        }
        if (houses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "暂无未出租房屋", "提示", JOptionPane.INFORMATION_MESSAGE);
            refreshAllHouses();
        }
    }

    private void showAddHouseDialog() {
        new AddHouseDialog(this, houseService, this::refreshAllHouses);
    }

    private void showFindHouseDialog() {
        String input = JOptionPane.showInputDialog(this, "请输入房源ID:", "查找房屋", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int houseId = Integer.parseInt(input);
                House house = houseService.findHouseById(houseId);
                if (house != null) {
                    String message = "房源ID: " + house.getHouseId() + "\n" +
                            "房主姓名: " + house.getOwnerName() + "\n" +
                            "电话: " + house.getPhone() + "\n" +
                            "地址: " + house.getAddress() + "\n" +
                            "月租金: " + house.getMonthlyRent() + "\n" +
                            "房屋状态: " + house.getStatus();
                    JOptionPane.showMessageDialog(this, message, "房屋详情", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "该房源不存在", "查找失败", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "房源ID必须是数字", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditHouseDialog() {
        int selectedRow = houseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要修改的房屋", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int houseId = (Integer) tableModel.getValueAt(selectedRow, 0);
        House house = houseService.findHouseById(houseId);
        new EditHouseDialog(this, houseService, house, this::refreshAllHouses);
    }

    private void showDeleteHouseDialog() {
        int selectedRow = houseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的房屋", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new DeleteHouseDialog(this, houseService, (Integer) tableModel.getValueAt(selectedRow, 0), this::refreshAllHouses);
    }
}
