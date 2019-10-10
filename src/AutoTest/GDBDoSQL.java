package AutoTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GDBDoSQL {
    /*
     * 增加
     */
    public void add(String strSQL) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 获取连接
            connection = GDBConnector.getConnection();
            preparedStatement = connection.prepareStatement(strSQL);
            System.out.println("[SQL-INSERT INTO]");
            int num = preparedStatement.executeUpdate();// 返回影响到的行数

            System.out.println(GMsg.MSG_DB[0] + num);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	GDBConnector.close(connection, preparedStatement, null);
        }
    }

    /*
     * 读取查询
     */
    public void retrieve(String strSQL) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> listResult = new ArrayList<String>();
        try {
            connection = GDBConnector.getConnection();
            preparedStatement = connection.prepareStatement(strSQL);
            System.out.println("[SQL-RETRIEVE]");
            resultSet = preparedStatement.executeQuery();

        	int countColumn = 0;
        	int countRow = 0;
        	countColumn = resultSet.getMetaData().getColumnCount();
            // 遍历结果集
            while (resultSet.next()) {
            	if(countColumn > 0) {
            		for(int i = 0; i < countColumn; i++) {
            			listResult.add(resultSet.getString(i+1));
            			System.out.print(listResult.get(i)+ "||");
            		}          		
            	}
            	countRow++;
            	System.out.println("\r\n");
            }
            System.out.println(GMsg.MSG_DB[1] + countRow);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	GDBConnector.close(connection, preparedStatement, resultSet);
        }

    }

    /*
     * 修改更新
     */
    public void update(String strSQL) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = GDBConnector.getConnection();
            preparedStatement = connection.prepareStatement(strSQL);
            System.out.println("[SQL-UPDATE]");
            int num = preparedStatement.executeUpdate();

            System.out.println(GMsg.MSG_DB[0] + num);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	GDBConnector.close(connection, preparedStatement, null);
        }
    }

    /*
     * 删除
     */
    public void delete(String strSQL) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = GDBConnector.getConnection();
            preparedStatement = connection.prepareStatement(strSQL);
            System.out.println("[SQL-DELETE]");
            int num = preparedStatement.executeUpdate();

            System.out.println(GMsg.MSG_DB[0] + num);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	GDBConnector.close(connection, preparedStatement, null);
        }

    }
    
    /*
     * 删除
     */
    public void count(String strTableName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = GDBConnector.getConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + strTableName);
            System.out.println("[SQL-COUNT]");
            resultSet = preparedStatement.executeQuery();
            int num = 0;
            while(resultSet.next()){
              num=resultSet.getInt(1);
            }

            System.out.println(GMsg.MSG_DB[1] + num);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	GDBConnector.close(connection, preparedStatement, null);
        }

    }

    public static void main(String[] args) {
    	String strTempAdd = ""
    			+ "INSERT INTO "
    			+ "CERT("
    			+ "CERT_ID, "
    			+ "CERT_TYPE_ID, "
    			+ "USER_ID, "
    			+ "DN_ELEMENT_ID, "
    			+ "BRANCH_ID, "
    			+ "USER_DN, "
    			+ "CERT_STATUS, "
    			+ "SERIAL_NO, "
    			+ "AUTH_CODE, "
    			+ "DURATION, "
    			+ "APPLY_TIME, "
    			+ "SENDCODE_TIME, "
    			+ "START_TIME, "
    			+ "END_TIME, "
    			+ "CERT_SOURCE, "
    			+ "KEY_ALG, "
    			+ "KEY_LENGTH, "
    			+ "SEQUENCE_NO, "
    			+ "EMAIL, "
    			+ "IS_DELETABLE, "
    			+ "DISTRIBUTE_MODE) " 
    			+ "VALUES ("
    			+ "1622717916586163981, "
    			+ "1, "
    			+ "1622717916530798512, "
    			+ "1622717916041809828, "
    			+ "1, "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN', "
    			+ "4, "
    			+ "'1036482459', "
    			+ "'1111111111', "
    			+ "24, "
    			+ "20000101000000, "
    			+ "20000101000000, "
    			+ "20000101000000, "
    			+ "20991231235959, "
    			+ "1, "
    			+ "'RSA', "
    			+ "2048, "
    			+ "2, "
    			+ "'test@test.com', "
    			+ "0, "
    			+ "0)";
    	String strTempRetrieve = ""
    			+ "SELECT "
    			+ "CERT_ID,"
    			+ "CERT_TYPE_ID,"
    			+ "USER_ID,"
    			+ "DN_ELEMENT_ID,"
    			+ "BRANCH_ID,"
    			+ "USER_DN,"
    			+ "CERT_STATUS,"
    			+ "SERIAL_NO,"
    			+ "AUTH_CODE,"
    			+ "DURATION,"
    			+ "APPLY_TIME,"
    			+ "SENDCODE_TIME,"
    			+ "START_TIME,"
    			+ "END_TIME,"
    			+ "CERT_SOURCE,"
    			+ "KEY_ALG,"
    			+ "KEY_LENGTH,"
    			+ "SEQUENCE_NO,"
    			+ "EMAIL,"
    			+ "IS_DELETABLE,"
    			+ "DISTRIBUTE_MODE " 
    			+ "FROM "
    			+ "CERT "
    			+ "WHERE "
    			+ "USER_DN = "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN'";
    	String strTempUpdate = ""
    			+ "UPDATE "
    			+ "CERT "
    			+ "SET "
    			+ "CERT_ID = 1622717916586163982,"
    			+ "CERT_TYPE_ID = 1,"
    			+ "USER_ID = 1622717916530798513,"
    			+ "DN_ELEMENT_ID = 1622717916041809828,"
    			+ "BRANCH_ID = 2,"
    			+ "CERT_STATUS = 3,"
    			+ "SERIAL_NO = '1036482460',"
    			+ "AUTH_CODE = '1111111112',"
    			+ "DURATION = 24,"
    			+ "APPLY_TIME = 20000101000000,"
    			+ "SENDCODE_TIME = 20000101000000,"
    			+ "START_TIME = 20000101000000,"
    			+ "END_TIME = 20991231235959,"
    			+ "CERT_SOURCE = 1,"
    			+ "KEY_ALG = 'SM2',"
    			+ "KEY_LENGTH = 4096,"
    			+ "SEQUENCE_NO = 3,"
    			+ "EMAIL = 'test1@test1.com',"
    			+ "IS_DELETABLE = 0,"
    			+ "DISTRIBUTE_MODE = 0 " 
    			+ "WHERE "
    			+ "USER_DN = "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN'";
    	String strTempDelete = ""
    			+ "DELETE FROM "
    			+ "CERT "
    			+ "WHERE "
    			+ "USER_DN = "
    			+ "'CN=051@录入管理员@Zlocal@1,OU=Employees,OU=Local RA,O=CFCA TEST OCA1,C=CN'";
    	String strTempTableName = "CERT";
    	GDBDoSQL test = new GDBDoSQL();
        test.add(strTempAdd);
        test.count(strTempTableName);
        test.retrieve(strTempRetrieve);
        test.update(strTempUpdate);
        test.retrieve(strTempRetrieve);
        test.delete(strTempDelete);
        test.retrieve(strTempRetrieve);
    }
}
