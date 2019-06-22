package com.donald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donald.pojos.Associate;
import com.donald.pojos.Employee;
import com.donald.util.ConnectionFactory;
import com.donald.util.LoggingUtil;

public class EmployeeDAOImpl implements EmployeeDAOInt {

	// connection
	private Connection conn = ConnectionFactory.getConnection();

	@Override
	public List<Employee> getAllEmployees() {
		LoggingUtil.trace("In getAllEmployees() method");
		
		
		List<Employee> employeeList = new ArrayList<>();
		Employee employee = null;
		
		String sql = "select * from employee;";
		
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				//if the employee in the row is an associate
				// 1 -> associate ID in database
				if (rs.getInt("employee_type_Id") == 1) {
					employee = new Associate(rs.getInt("employee_Id"), rs.getString("username"), rs.getString("password"), rs.getString("employeeType"));
				}
				
				employeeList.add(employee);
			}
		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}
		
		return employeeList;
}

}
