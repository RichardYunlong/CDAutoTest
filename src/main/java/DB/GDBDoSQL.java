package DB;

import DT.GLog;
import Base.GMsg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  SQL执行器
 *
 *  <p>封装对数据库的查询、计数与增删改三类操作，统一负责连接获取、结果集遍历与资源释放。</p>
 *
 *  <p>使用方式：</p>
 *  <pre>{@code
 *  GDBDoSQL doSQL = new GDBDoSQL();
 *  doSQL.doSQL("retrieve", "SELECT * FROM T_USER", "");
 *  doSQL.doSQL("count",    "",                      "T_USER");
 *  doSQL.doSQL("update",   "UPDATE T_USER SET ...", "");
 *  }</pre>
 *
 *  <p>本类不是线程安全的：一次 {@link #doSQL(String, String, String)} 调用期间独占
 *  {@link #connection}、{@link #preparedStatement} 与 {@link #resultSet}，请勿并发复用同一实例。</p>
 *
 *  @see GDBConnector
 */
public class GDBDoSQL {
	
    /**
     * 唯一数据库连接对象
     */
	private Connection connection = null;
	
    /**
     * 唯一数据库准备语句
     */
	private PreparedStatement preparedStatement = null;
	
    /**
     * 唯一数据库返回集合
     */
	private ResultSet resultSet = null;
	
    /**
     * 唯一数据库计数
     *
     * <p>查询时为结果集行数，计数时为 {@code COUNT(*)} 的值，增删改时为受影响行数。</p>
     */
	private int effectRecord = 0;
	
	
	/**
     * 执行指定SQL语句
     *
     * <p>按 {@code strSQLType} 分派执行逻辑：</p>
     * <ul>
     *   <li>{@code retrieve}：执行 {@code strSQL} 查询，逐行逐列写入日志，{@code effectRecord} 记为行数；</li>
     *   <li>{@code count}：对 {@code strTableName} 执行 {@code SELECT COUNT(*)}，{@code effectRecord} 记为总数；</li>
     *   <li>其他值（{@code add}/{@code update}/{@code delete}）：执行 {@code strSQL} 更新，{@code effectRecord} 记为受影响行数。</li>
     * </ul>
     *
     * <p>SQL 执行异常不会向上抛出，只记录到日志；无论成功与否都会在 {@code finally} 中关闭连接。</p>
     * 
     * @param strSQLType 语句类型标识，取值见上；传 {@code null} 或无法识别的值时按更新处理
     * @param strSQL SQL语句；{@code count} 类型下可传空串
     * @param strTableName 表名称，仅 {@code count} 类型使用，其他情况下传空串
     * @see #effectRecord
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
