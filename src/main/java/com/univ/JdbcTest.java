package com.univ;

/**
 * Univ
 * 16/12/25 11:04
 */

import org.junit.Test;

import java.sql.*;

/**
 * 用于测试jdbc几大核心组件的功能
 * 这里处理完后需要关闭资源，不过为了突出重点，这里省略。
 */
public class JdbcTest {

    /**
     * 测试Statement的两个主要方法以及ResultSet的用法.
     * Statement
     * 是静态sql语句(没有占位符)的对象表示,只能用来执行静态sql语句。
     * <p>
     * executeQuery(sql): 用以执行静态查询(select)语句，有参，不能被PreparedStatement使用；
     * executeUpdate(sql): 用来执行静态insert,update,delete等DML语句和DDL语句，有参，不能被PreparedStatement使用；
     * <p>
     * ResultSet
     * ResultSet没有hasNext()方法，底层是游标，只有next()方法
     */
    @Test
    public void statementTest() throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        Statement stat = connection.createStatement();

        String sql = "insert into test(id, name, age)values(1,'abc',23)";//这里的sql不能有占位符
        int affectedRows = stat.executeUpdate(sql);
        System.out.println("受影响的行数： " + affectedRows);

        sql = "SELECT * FROM test";//这里的sql不能有占位符
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
            System.out.print(rs.getInt("id") + " " + rs.getString("name") + "  " + rs.getInt("age"));
            System.out.println();
        }
    }

    /**
     * PreparedStatement
     * 是动态sql语句(有占位符)的对象表示,只能用来执行动态sql语句。
     * executeQuery():执行动态查询(select)语句，不需要参数(构造PreparedStatement时有提供)；
     * executeUpdate():执行动态insert,update,delete等DML语句各DLL语句，不需要参数
     * <p>
     * 占位符的下标从1开始。
     *
     * @throws SQLException
     */
    @Test
    public void preparedStatementTest() throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT * FROM test WHERE id = ?";//这里的sql有占位符
        PreparedStatement pstat = connection.prepareStatement(sql);
        pstat.setInt(1, 2);//占位符的下标从1开始。
        ResultSet rs = pstat.executeQuery();
        while (rs.next()) {
            System.out.print(rs.getInt("id") + " " + rs.getString("name") + "  " + rs.getInt("age"));
            System.out.println();
        }
    }

}
