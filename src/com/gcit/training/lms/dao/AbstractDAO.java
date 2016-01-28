package com.gcit.training.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractDAO {

	Connection connection;
	public AbstractDAO(Connection connection) {
		this.connection = connection;
	}
	
	protected Connection getConnection() {
		return connection;
	}
	
	public void save(String query, Object[] values) throws SQLException {
		Connection conn = getConnection();
		
		PreparedStatement stmt = conn.prepareStatement(query);

		
		
		int count = 1;
		for(Object obj : values) {
			stmt.setObject(count, obj);
			count++;
		}
		

		
		stmt.executeUpdate();
		conn.close();
	}
	
	protected List<?> read(String sql, Object[] values) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stmt = conn.prepareStatement(sql);

		if(values != null) {
			int count = 1;
			for(Object obj : values) {
				stmt.setObject(count, obj);
				count++;
			}
		}
		
		ResultSet rs = stmt.executeQuery();
		
		
		return processResult(rs);
	}
	
	protected int count(String sql, Object[] values) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stmt = conn.prepareStatement(sql);

		if(values != null) {
			int count = 1;
			for(Object obj : values) {
				stmt.setObject(count, obj);
				count++;
			}
		}
		
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getInt("count");

	}
	
	
	protected abstract List<?> processResult(ResultSet rs) throws SQLException;
	
}
