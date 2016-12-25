package com.univ;

/**
 * Univ
 * 16/12/25 11:05
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * jdbc工具类，用来获取数据库连接
 * <p>
 * 数据库jdbc中的表为test；
 * 因为获取数据库连接是一个昂贵的操作，因此这里将Connection实现为单例(弱单例)，程序需要自己关闭资源。
 */
public class JdbcUtil {

    private static String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static String USER = "test";
    private static String PASSWORD = "123";
    private static Connection conn;

    static {
        try {
            //1. 加载Mysql驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //这里没有考虑多线程的问题，因为只是用来测试小功能
    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
