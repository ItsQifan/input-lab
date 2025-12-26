package com.zhouchuanxiang.inputlab.dp._01singleton;

import java.sql.Connection;

/**
 * @author zhouchuanxiang
 * 单例模式，推荐枚举方式实现
 */
public enum DatabaseConnection {

    INSTANCE;

    private Connection connection;
    private String connectionString = "jdbc:mysql://localhost:3306/mydb";

    // 构造方法（私有，JVM调用）
    DatabaseConnection() {
        System.out.println("枚举单例初始化");
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            // 模拟数据库连接
            connection = null; // 实际为 DriverManager.getConnection(connectionString);
            System.out.println("数据库连接已建立");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 业务方法
    public Connection getConnection() {
        return connection;
    }

    public void executeQuery(String sql) {
        System.out.println("执行查询: " + sql);
    }

    // 其他业务方法...



}

// 测试代码
class Main {
    public static void main(String[] args) {
        // 获取单例实例
        DatabaseConnection instance1 = DatabaseConnection.INSTANCE;
        DatabaseConnection instance2 = DatabaseConnection.INSTANCE;

        // 验证单例
        System.out.println(instance1 == instance2); // true
        System.out.println(instance1.hashCode() == instance2.hashCode()); // true

        // 使用单例方法
        instance1.executeQuery("SELECT * FROM users");
    }
}