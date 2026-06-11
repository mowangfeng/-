package com.house.model;

import java.io.Serializable;

/**
 * 房屋数据模型
 */
public class House implements Serializable {
    private static final long serialVersionUID = 1L;
    private int houseId;          // 房源ID
    private String ownerName;     // 房主姓名
    private String phone;         // 电话
    private String address;       // 地址
    private double monthlyRent;   // 月租金
    private String status;        // 房屋状态（未出租/已出租）

    // 构造方法
    public House() {}

    public House(int houseId, String ownerName, String phone, String address, double monthlyRent, String status) {
        this.houseId = houseId;
        this.ownerName = ownerName;
        this.phone = phone;
        this.address = address;
        this.monthlyRent = monthlyRent;
        this.status = status;
    }

    // Getter和Setter方法
    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseId=" + houseId +
                ", ownerName='" + ownerName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", monthlyRent=" + monthlyRent +
                ", status='" + status + '\'' +
                '}';
    }
}
