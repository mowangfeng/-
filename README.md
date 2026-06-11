# 🏠 房屋出租管理系统

基于Java Swing的房屋出租管理系统，提供房屋信息的增删改查功能。

## 📦 项目结构

```
house-rental-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── house/
│   │   │           ├── ui/
│   │   │           │   ├── LoginFrame.java          # 登录界面
│   │   │           │   ├── MainFrame.java           # 主界面
│   │   │           │   ├── AddHouseDialog.java      # 添加房屋对话框
│   │   │           │   ├── EditHouseDialog.java     # 编辑房屋对话框
│   │   │           │   └── DeleteHouseDialog.java   # 删除房屋对话框
│   │   │           ├── model/
│   │   │           │   └── House.java               # 房屋数据模型
│   │   │           ├── service/
│   │   │           │   ├── HouseService.java        # 房屋业务逻辑
│   │   │           │   └── DataManager.java         # 数据管理
│   │   │           └── HouseRentalApp.java          # 应用启动类
│   │   └── resources/
│   │       ├── images/
│   │       │   ├── login_bg.png                     # 登录界面背景
│   │       │   ├── home_banner.png                  # 首页横幅
│   │       │   └── house_icon.png                   # 房屋图标
│   │       └── data/
│   │           └── houses.dat                       # 数据文件
└── README.md
```

## 🚀 功能特性

- ✅ 用户登录系统
- ✅ 新增房屋信息
- ✅ 查找房屋详情
- ✅ 删除房屋记录
- ✅ 修改房屋信息
- ✅ 房屋列表展示

## 📝 使用说明

1. 运行 `HouseRentalApp.java`
2. 输入用户名和密码登录（默认：admin/admin）
3. 在主界面进行房屋管理操作

## 🛠 技术栈

- Java 8+
- Swing GUI框架
- 序列化存储
