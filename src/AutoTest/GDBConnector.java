package AutoTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  数据库连接驱动
 */
public class GDBConnector {
    /** 
     * 获得数据库连接 
     *  
     * @return conn
     */  
    public static Connection getConnection() {  
        Connection conn = null;  
        String driverName = "oracle.jdbc.OracleDriver";  
        String url = "jdbc:oracle:thin:@192.168.115.90:1521:orcl";  
        String username = "AutoTest";  
        String password = "AutoTest";  
  
        try {  
            Class.forName(driverName);  
            conn = DriverManager.getConnection(url, username, password);  
        } catch (ClassNotFoundException e) {  
            System.out.println("FAIL TO LOAD DB DRIVER");  
            e.printStackTrace();  
        } catch (SQLException e) {  
            System.out.println("FAIL TO GET DB CONNECTOR");  
            e.printStackTrace();  
        }  
        return conn;  
    }  
  
    /** 
     * 关闭数据库连接 
     *  
     * @param conn 
     * @param ps 
     * @param rs 
     */  
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {  
        // 关闭原则:先开后关,后开先关  
        if (rs != null) {  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if (ps != null) {  
            try {  
                ps.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if (conn != null) {  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
//    public static void main(String[] args) {  
//        System.out.println(getConnection());  
//    }  
}
