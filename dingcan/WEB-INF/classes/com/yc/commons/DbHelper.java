package com.yc.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 数据库操作：增删改 数据库连接 关闭等操作
 * 
 * @author ASUS
 *
 */
public class DbHelper {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 获取连接的方法
	public Connection getConn() {

		// Context ctx = new InitialContext();
		// DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/product");
		// conn = ds.getConnection();
		try {
			DataSource ds = (DataSource) BasicDataSourceFactory.createDataSource(MyProperties.getInstance());
			conn = ds.getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	// 关闭结果集 处理对象 连接对象
	public void closeAll() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int update(List<String> sqls, List<List<Object>> listParams) throws SQLException {
		int result = 0;
		conn = this.getConn();
		try {
			conn.setAutoCommit(false);// 事务手动提交
			if (null != sqls && sqls.size() > 0) {
				for (int i = 0; i < sqls.size(); i++) {
					pstmt = conn.prepareStatement(sqls.get(i));
					this.setParams(pstmt, listParams.get(i));
					result = pstmt.executeUpdate();
				}
			}
			conn.commit();// 手动提交事务
		} catch (Exception e) {

			try {
				conn.rollback();// 发生异常 事务回滚

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			conn.setAutoCommit(true);// 将事务的状态设置自动提交
			this.closeAll();
		}

		return result;

	}

	public <T> List<T> find(String sql, List<Object> params, Class c) throws Exception {
		List<T> list = new ArrayList<T>();
		int retryCount = 10;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			// 设置参数
			setParams(pstmt, params);
			// 执行
			rs = pstmt.executeQuery();
			T t; // 实例化对象
			// 获取所有字段名称
			List<String> columnNames = getAllColumnNames(rs);
			// 通过class实例获取当前实例中所有的方法
			Method[] methods = c.getMethods();
			// for(Method m:methods){
			// System.out.println(m.getName());
			// }
			String methodName = null; // 方法名
			String columnName = null; // 列名
			Object val = null; // 值
			String typeName = null;// 值的类型名称
			while (rs.next()) {
				
				t = (T) c.newInstance(); // 实例化对象
				// 设置值
				// 循环所有的列名
				for (int i = 0; i < columnNames.size(); i++) {
					columnName = columnNames.get(i);
					val = rs.getObject(columnName);// 根据列名取值
					if (null == val) {
						continue;
					}
					
					// 循环所有的方法
					for (Method m : methods) {
						methodName = m.getName(); // 获取方法名
						// set+columnName ==>setTID setTNAME
						if (("set" + columnName).equalsIgnoreCase(methodName)) {
							// 获取值的类型
							typeName = val.getClass().getName();
							 //System.out.println(m.getName()+"=="+typeName);
							if ("java.lang.Integer".equals(typeName)) {
								m.invoke(t, rs.getInt(columnName));
							} else if ("java.math.BigDecimal".equals(typeName)) {
								m.invoke(t, rs.getObject(columnName).toString());
							} else if ("java.lang.String".equals(typeName)) {
								m.invoke(t, rs.getString(columnName));
							} else if ("oracle.sql.CLOB".equals(typeName)) {
								// 根据数据类型扩展

								String str = ClobToString(rs.getClob(columnName));
								m.invoke(t, str);
							} else if ("java.sql.Date".equals(typeName)){
								m.invoke(t, rs.getObject(columnName).toString());
							}
						}
					}
				}
				// 将对象提取到List集合中
				list.add(t);
			}
		}catch (SQLException sqlEx) {
            String sqlState = sqlEx.getSQLState();
           // 这个08S01就是这个异常的sql状态。单独处理手动重新链接就可以了。
            if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {                
                retryCount--;            
            } else{                
            	retryCount = 0;            
            }        
        }finally {
			closeAll();
		}

		return list;
	}

	/**
	 * 将Clob转为字符串
	 * 
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */

	private String ClobToString(Clob sc) throws SQLException, IOException {
		String reString = "";
		Reader is = sc.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出赋值给STRINGbUFFER
			sb.append(s);
			s = br.readLine();

		}
		reString = sb.toString();
		return reString;

	}

	/**
	 * 查询多条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> selecTMultiObject(String sql, List<Object> params) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		conn = this.getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			// 获取字段名
			List<String> columnNames = this.getAllColumnNames(rs);
			Map<String, Object> map = null;
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (String cn : columnNames) {
					map.put(cn, rs.getObject(cn));
				}
				list.add(map);
			}
		} finally {
			this.closeAll();
		}
		return list;

	}

	// 修改
	public int update(String sql, List<Object> params) throws Exception {
		int result = 0;
		conn = this.getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			this.setParams(pstmt, params);
			result = pstmt.executeUpdate();

		} finally {
			this.closeAll();
		}
		return result;

	}

	// 聚合函数
	public double getCount(String sql, List<Object> params) throws Exception {
		double i = 0;
		conn = this.getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			this.setParams(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				i = rs.getDouble(1); // 获取第一列的值
			}

		} finally {
			this.closeAll();
		}
		return i;
	}

	public Map<String, Object> selectOne(String sql, List<Object> params) throws Exception {
		Map<String, Object> map = null;
		conn = this.getConn();

		try {
			pstmt = conn.prepareStatement(sql);
			this.setParams(pstmt, params); // 设置参数值
			rs = pstmt.executeQuery();
			// 获取结果集中所有的字段名
			List<String> columnNames = this.getAllColumnNames(rs);
			// 处理结果集
			if (rs.next()) {
				map = new HashMap<String, Object>();
				// 使用字段名作为键
				for (String cn : columnNames) {
					map.put(cn, rs.getObject(cn));
				}
			}

		} finally {
			this.closeAll();
		}
		return map;
	}

	// 设置参数
	public void setParams(PreparedStatement pstmt, List<Object> params) throws Exception {
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				// 图片处理 需要用到二进制流
				if (params.get(i) instanceof File) {
					File file = (File) params.get(i);
					InputStream in = new FileInputStream(file);
					pstmt.setBinaryStream(i + 1, in, (int) file.length());// 设置二进制流
				} else {
					pstmt.setObject(i + 1, params.get(i));// 设置参数值
				}
			}
		}
	}

	// 获取结果集中的字段名
	public List<String> getAllColumnNames(ResultSet rs) throws SQLException {
		List<String> names = new ArrayList<String>();
		// 通过结果集对象获取字段名
		int length = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= length; i++) {
			String str = rs.getMetaData().getColumnLabel(i);
			names.add(str);// 讲字段名存入list集合中

		}
		return names;
	}

	
	// 聚合函数
		public double SelectPloymer(String sql, List<Object> params) throws Exception {
			double result = 0;
			
			try {
				conn = this.getConn();
				pstmt = conn.prepareStatement(sql);
				setParams(pstmt, params);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = rs.getDouble(1); // 获取第一列的值
				}

			} finally {
				closeAll();
			}
			return result;
		}
}
