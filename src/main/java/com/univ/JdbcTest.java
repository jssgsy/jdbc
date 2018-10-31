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

    /**
     * 测试jdbc的事务.重点：所谓事务没提交，即所执行的sql语句不会对数据库有任何更改，相当于没有执行sql语句一样。
     *  jdbc默认处于auto-commit模式，即自动提交事务。可通过con.setAutoCommit(false)控制。
     *
     *  这里的重点是事务何时提交，事务提交发生在Statement完成时。而Statement完成发生在：
     *  1. 处于非auto-commit模式：
     *          需要显示调用con.commit()或者rollback()方法，Statement完成；
     *
     *  2. 处于auto-commit
     *          a. 对于Insert、Update、Delete和DDL语句, 一旦执行则Statement完成；
     *          b. 对于Select statements, the statement is complete when the associated result set is closed.
     *
     *  具体参见setAutoCommit()方法
     *
     * @throws SQLException
     */
    @Test
    public void transactionTest() throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        connection.setAutoCommit(false);

        //code which need a transaction
        Statement stat = connection.createStatement();
        String sql = "insert into test(id, name, age)values(200,'aaa',23)";//这里的sql不能有占位符
        int affectedRows = stat.executeUpdate(sql);
        System.out.println("受影响的行数： " + affectedRows);

        /*
         * 必须显示调用commit()或者rollback()方法，否则事务不会提交。
         * 此时上述sql语句不会真的向数据库中插入记录。
         */
        connection.commit();

    }
}
