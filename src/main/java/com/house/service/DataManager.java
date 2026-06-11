package com.house.service;

import com.house.model.House;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据管理类 - 处理文件的序列化和反序列化
 */
public class DataManager {
    private static final String DATA_FILE = "houses.dat";

    /**
     * 从文件加载房屋数据
     */
    @SuppressWarnings("unchecked")
    public static List<House> loadHouses() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<House>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 保存房屋数据到文件
     */
    public static boolean saveHouses(List<House> houses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(houses);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
