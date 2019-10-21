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
	public static String driverClassName = "";
	public static String url = "";
	public static String username = "";
	public static String pwd;
	
    /** 
     * 获得数据库连接 
     *  
     * @return conn
     */  
    public static Connection getConnection() {  
        Connection conn = null;  
  
        try {  
            Class.forName(driverClassName);  
            conn = DriverManager.getConnection(url, username, pwd);  
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
