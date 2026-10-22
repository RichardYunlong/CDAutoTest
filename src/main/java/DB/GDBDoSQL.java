package DB;

import DT.GLog;
import Base.GMsg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  SQL执行器
 */
public class GDBDoSQL {
	
    /*
     * 唯一数据库连接对象
     */
	private Connection connection = null;
	
    /*
     * 唯一数据库准备语句
     */
	private PreparedStatement preparedStatement = null;
	
    /*
     * 唯一数据库返回集合
     */
	private ResultSet resultSet = null;
	
    /*
     * 唯一数据库计数
     */
	private int effectRecord = 0;
	
	
	/*
     * 执行指定SQL语句
     * 
     * @param strSQLType 语句类型标识，为空时取值为"retrieve"
     * @param strSQL SQL语句
     * @param strTableName 表名称，计数专用，其他情况下为空
     */
	public void doSQL(String strSQLType, String strSQL, String strTableName) {
		GLog.logRecord(8, "[SQL-[" + strSQLType + "]]");
        try {
            // 获取连接
            connection = GDBConnector.getConnection();
            
            switch(strSQLType) {
	            case "retrieve":{
	            	preparedStatement = connection.prepareStatement(strSQL);
	                // 原实现用 executeUpdate 执行查询、且从未给 resultSet 赋值，
	                // 下一行 resultSet.getMetaData() 必然 NPE，查询分支从未成功执行过。
	                resultSet = preparedStatement.executeQuery();
	            	int countColumn;
	            	int countRow = 0;
	            	countColumn = resultSet.getMetaData().getColumnCount();
	                // 遍历结果集
	                while (resultSet.next()) {
	                	for(int i = 1; i <= countColumn; i++) {
	                		GLog.logRecord(8, resultSet.getString(i) + "||");
	                	}
	                	countRow++;
	                	GLog.logRecord(8, "\r\n");
	                }
	                effectRecord = countRow;
	            	break;
	            }
	            case "count":{
	                preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + strTableName);
	                resultSet = preparedStatement.executeQuery();
	                while(resultSet.next()){
	                	effectRecord=resultSet.getInt(1);
	                }
	            	break;
	            }
	            default:{//add、update、delete
	            	preparedStatement = connection.prepareStatement(strSQL);
	                effectRecord = preparedStatement.executeUpdate();
	            	break;
	            }
            }

            GLog.logRecord(8, GMsg.MSG_DB[0] + effectRecord);
        } catch (SQLException e) {
        	GLog.logSysFunctionException("add", e);
        } finally {
        	GDBConnector.close(connection, preparedStatement, resultSet);
        }
	}
}
