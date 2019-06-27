package com.donald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donald.pojos.Associate;
import com.donald.pojos.BenefitsCoordinator;
import com.donald.pojos.Employee;
import com.donald.pojos.Manager;
import com.donald.pojos.Supervisor;
import com.donald.util.ConnectionFactory;
import com.donald.util.LoggingUtil;

public class EmployeeDAOImpl implements EmployeeDAOInt {

	// connection
	private static Connection conn = ConnectionFactory.getConnection();

	@Override
	public List<Employee> getAllEmployees() {
		LoggingUtil.trace("In getAllEmployees() method");
		
		List<Employee> employeeList = new ArrayList<>();
		Employee employee = null;
		
		//join to get employee type!
		String sql = "select emp.*, emp_type.employee_type " + 
				"from employee emp " + 
				"inner join employee_type emp_type on emp.employee_type_id = emp_type.employee_type_id;";
		
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				//if the employee in the row is an associate
				// 1 -> associate ID in database
				
				//should be switch() case
				if (rs.getInt("employee_type_id") == 1) {
					employee = new Associate(rs.getInt("employee_id"), rs.getString("username"), rs.getString("password"), rs.getString("employee_type"));
				} else if (rs.getInt("employee_type_id") == 2) {
					employee = new Supervisor(rs.getInt("employee_id"), rs.getString("username"), rs.getString("password"), rs.getString("employee_type"));
				} else if (rs.getInt("employee_type_id") == 3) {
					employee = new Manager(rs.getInt("employee_id"), rs.getString("username"), rs.getString("password"), rs.getString("employee_type"));
				} else if (rs.getInt("employee_type_id") == 4) {
					employee = new BenefitsCoordinator(rs.getInt("employee_id"), rs.getString("username"), rs.getString("password"), rs.getString("employee_type"));
				}
				
				employeeList.add(employee);
			}
		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}
		
		return employeeList;
}

}
