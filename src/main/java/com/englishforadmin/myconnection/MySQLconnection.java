package com.englishforadmin.myconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLconnection {
    private static Connection con = null;

    private MySQLconnection() {
    }

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String dbUrl = System.getenv("DB_URL");
                String dbUsername = System.getenv("DB_USERNAME");
                String dbPassword = System.getenv("DB_PASSWORD");
                con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            } catch (ClassNotFoundException e) {
                System.out.println("Không tìm thấy JDBC driver");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Không thể kết nối đến cơ sở dữ liệu");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Lỗi không xác định: ");
                e.printStackTrace();
            }
        }
        return con;
    }
}
