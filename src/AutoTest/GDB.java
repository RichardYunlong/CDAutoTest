package AutoTest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GDB {
    /*
     * 增加
     */
    public void add() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 获取连接
            connection = GDBConnector.getConnection();

            String carnumber_head = "甬-K-1F-";
            for (int i = 1; i <= 100; i++) {

                if (i < 10) {
//                  System.out.println("value------>>>" + carnumber_head+"000" + i);
                     // 准备sql语句
                    String sql = "INSERT INTO cx_carnumberpool(carnumber) VALUES(?)";
                    // 获取PrepareStatement对象
                    preparedStatement = connection.prepareStatement(sql);
                    // 填充占位符
                    preparedStatement.setString(1, carnumber_head+"000" + i);
                    int num = preparedStatement.executeUpdate();// 返回影响到的行数

                    System.out.println("一共影响到" + num + "行");

                } else if (i < 100) {
                     // 准备sql语句
                    String sql = "INSERT INTO cx_carnumberpool(carnumber) VALUES(?)";
                    // 获取PrepareStatement对象
                    preparedStatement = connection.prepareStatement(sql);
                    // 填充占位符
                    preparedStatement.setString(1, carnumber_head+"00" + i);
                    int num = preparedStatement.executeUpdate();// 返回影响到的行数

                    System.out.println("一共影响到" + num + "行");
                } else if (i < 1000) {
                     // 准备sql语句
                    String sql = "INSERT INTO cx_carnumberpool(carnumber) VALUES(?)";
                    // 获取PrepareStatement对象
                    preparedStatement = connection.prepareStatement(sql);
                    // 填充占位符
                    preparedStatement.setString(1, carnumber_head+"0" + i);
                    int num = preparedStatement.executeUpdate();// 返回影响到的行数
                    System.out.println("一共影响到" + num + "行");
                } 

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	GDBConnector.close(connection, preparedStatement, null);
        }
    }

    /*
     * 读取查询
     */
    public void retrieve() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = GDBConnector.getConnection();
            String sql = "SELECT user_name,user_password,user_birth FROM user";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            // 遍历结果集
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);
                Date userbirth = resultSet.getDate(3);

                System.out.println(username + ":" + password + ":" + userbirth);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
        	GDBConnector.close(connection, preparedStatement, resultSet);
        }

    }

    /*
     * 修改更新
     */
    public void update() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = GDBConnector.getConnection();
            String sql = "UPDATE USER SET user_password = ? WHERE user_name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "passwordupdate");
            preparedStatement.setString(2, "mary");
            int num = preparedStatement.executeUpdate();

            System.out.println("一共影响到" + num + "行");
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
        	GDBConnector.close(connection, preparedStatement, null);
        }
    }

    /*
     * 删除
     */
    public void delete() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = GDBConnector.getConnection();
            String sql = "DELETE FROM user WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 3);
            int num = preparedStatement.executeUpdate();

            System.out.println("一共影响到" + num + "行");
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
        	GDBConnector.close(connection, preparedStatement, null);
        }

    }

    public static void main(String[] args) {
         GDB test = new GDB();
         test.add();
    }
}
