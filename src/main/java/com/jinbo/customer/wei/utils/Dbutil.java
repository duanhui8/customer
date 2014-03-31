package com.jinbo.customer.wei.utils;



import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Dbutil {
    private static ThreadLocal<Connection> thread = new ThreadLocal<Connection>();
    
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

	public static ComboPooledDataSource getDataSource() {
		return dataSource;
	}
	public static Connection getThreadConnection() {
		Connection con = thread.get();
		if(con==null){
			try {
				thread.set(dataSource.getConnection());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return thread.get();
	}
	
	public static void begin(){
		Connection con = thread.get();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollback(){
		Connection con = thread.get();
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void commit(){
		Connection con = thread.get();
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void remove(){
		
			thread.remove();
		
	}
}
