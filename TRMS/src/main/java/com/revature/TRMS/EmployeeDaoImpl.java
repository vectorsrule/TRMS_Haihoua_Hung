package com.revature.TRMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class EmployeeDaoImpl implements EmployeeDAO {

	public Employee getEmployee(int id) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select * from Employee where employeeID = " + id;
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		while(rs.next()) {
			//rs.getInt(8) will return 0 if the value in database is null : supervisor
			return new Employee(id,rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getInt(7), rs.getString(8));
		}
		
		return null;
	}

	public int saveEmployee(Employee e) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		String sql = "insert into EMPLOYEE values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, e.getID());
		pstmt.setString(2, e.getFirstName());
		pstmt.setString(3, e.getLastName());
		pstmt.setString(4, e.getUsername());
		pstmt.setString(5, e.getPassword());
		pstmt.setString(6, e.getTitle());
		if(e.getSupervisor() <= 0) {
			pstmt.setObject(7, null);
		}
		else {
			pstmt.setInt(7, e.getSupervisor());
		}
		
		pstmt.setString(8, e.getDepartment());
		return pstmt.executeUpdate();
	}
	
	

	public void updateEmployee(Employee fc) throws SQLException {
		saveEmployee(fc);
	}

	public List<Employee> getAllEmployee() throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select * from Employee";
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		List<Employee> list = new ArrayList<Employee>();
		while(rs.next()) {
			list.add(new Employee(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getInt(7), rs.getString(8)));
		}
		return list;
	}

	public int deleteEmployee(int ids) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		String sql = "Delete from EMPLOYEE where employeeid = " +ids;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	@Override
	public int login(String username, String password) throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select password, Employeeid from Employee where username = \'" + username +"\'";
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		while(rs.next()) {
			if(rs.getString(1).equals(password)) {
				return rs.getInt(2);
			}
		}
		
		return -1;
	}
}
