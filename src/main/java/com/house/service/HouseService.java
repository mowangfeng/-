package com.house.service;

import com.house.model.House;
import java.util.List;

/**
 * 房屋管理业务逻辑
 */
public class HouseService {
    private List<House> houses;

    public HouseService() {
        this.houses = DataManager.loadHouses();
    }

    /**
     * 获取所有房屋
     */
    public List<House> getAllHouses() {
        return houses;
    }

    /**
     * 根据ID查找房屋
     */
    public House findHouseById(int houseId) {
        for (House house : houses) {
            if (house.getHouseId() == houseId) {
                return house;
            }
        }
        return null;
    }

    /**
     * 新增房屋
     */
    public boolean addHouse(House house) {
        // 检查房源ID是否重复
        if (findHouseById(house.getHouseId()) != null) {
            return false;
        }
        houses.add(house);
        return DataManager.saveHouses(houses);
    }

    /**
     * 删除房屋
     */
    public boolean deleteHouse(int houseId) {
        House house = findHouseById(houseId);
        if (house != null) {
            houses.remove(house);
            return DataManager.saveHouses(houses);
        }
        return false;
    }

    /**
     * 更新房屋信息
     */
    public boolean updateHouse(House updatedHouse) {
        House house = findHouseById(updatedHouse.getHouseId());
        if (house != null) {
            house.setOwnerName(updatedHouse.getOwnerName());
            house.setPhone(updatedHouse.getPhone());
            house.setAddress(updatedHouse.getAddress());
            house.setMonthlyRent(updatedHouse.getMonthlyRent());
            house.setStatus(updatedHouse.getStatus());
            return DataManager.saveHouses(houses);
        }
        return false;
    }

    /**
     * 获取所有未出租房屋
     */
    public List<House> getAvailableHouses() {
        List<House> availableHouses = new ArrayList<>();
        for (House house : houses) {
            if ("未出租".equals(house.getStatus())) {
                availableHouses.add(house);
            }
        }
        return availableHouses;
    }

    /**
     * 获取最大房源ID
     */
    public int getMaxHouseId() {
        int maxId = 0;
        for (House house : houses) {
            if (house.getHouseId() > maxId) {
                maxId = house.getHouseId();
            }
        }
        return maxId;
    }
}
