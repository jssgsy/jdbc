package com.univ;

/**
 * Univ
 * 16/12/25 10:18
 */

import java.sql.*;

/**
 * 用以演示jdbc使用的完整流程
 */
public class BasicJdbcProcess {

    public static void main(String[] args) {
        try {
            //1. 加载Mysql驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "test";
        String password = "123";

        Connection connection = null;
        try {
            //2. 从DriverManager获取数据库连接Connection
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        try {
            //3. 从Connection获取statement对象
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "SELECT * FROM test";
        ResultSet rs = null;
        try {
            //4. 执行sql语句
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //5. 对查询结果进行处理
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //6. 关闭资源，注意ResultSet、Statement和Connection都需要关闭
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
