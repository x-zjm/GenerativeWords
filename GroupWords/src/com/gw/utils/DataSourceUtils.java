package com.gw.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	private static DataSource ds = new ComboPooledDataSource();
	/**
	 * ThreadLocal用于保存某个线程共享变量：对于同一个static ThreadLocal，不同线程只能从中get，set，remove自己的变量，
	 * 	而不会影响其他线程的变量。
	 *	1、ThreadLocal.get: 获取ThreadLocal中当前线程共享变量的值。
	 *	2、ThreadLocal.set: 设置ThreadLocal中当前线程共享变量的值。
	 *	3、ThreadLocal.remove: 移除ThreadLocal中当前线程共享变量的值。
	 *	4、ThreadLocal.initialValue: ThreadLocal没有被当前线程赋值时或当前线程刚调用remove方法后调用get方法，返回此方法值。
	 */
	private static ThreadLocal<Connection> tl = new ThreadLocal<>(); 
	
	/**
	 * 获取数据源
	 * @return
	 */
	public static DataSource getDataSource(){
		return ds;
	}
	
	/**
	 * 获取当前线程上连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		/**
		 * 从当前线程中获取连接
				若之前没有绑定过,去数据源获取一个连接,将他绑定到当前线程中
		 */
		//从当前线程中获取连接
		Connection conn = tl.get();
		//若之前没有绑定过
		if(conn == null){
			//去数据源获取一个连接	
			conn = ds.getConnection();
			//将他绑定到当前线程中
			tl.set(conn);
		}
		return conn;
	}
	
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void beginTransaction() throws SQLException{
		/**
		 * 开启事务
				从当前线程中获取连接
				setAutoCommit(false)
		 */
		Connection conn = getConnection();
		conn.setAutoCommit(false);
	}
	
	/**
	 * 提交事务且释放资源
	 */
	public static void commitAndClose(){
		try {
			//从当前线程中获取连接
			Connection conn = getConnection();
			
			//提交事务
			//释放资源
			DbUtils.commitAndCloseQuietly(conn);
			
			//解绑
			tl.remove();
		} catch (SQLException e) {
			
		}
	}
	
	/**
	 * 回滚事务且释放资源
	 */
	public static void rollbackAndClose(){
		try {
			//从当前线程中获取连接
			Connection conn = getConnection();
			
			//回滚事务
			//释放资源
			DbUtils.rollbackAndCloseQuietly(conn);
			
			//解绑
			tl.remove();
		} catch (SQLException e) {
			
		}
	}
}
