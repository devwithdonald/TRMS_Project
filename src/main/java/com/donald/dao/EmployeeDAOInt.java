package com.donald.dao;

import java.util.List;

import com.donald.pojos.Employee;

public interface EmployeeDAOInt {
	
	public List<Employee> getAllEmployees();
	
	public int updateEmployeePendingBalance(Employee loggedInEmployee, int cost);
	
	

	
}
