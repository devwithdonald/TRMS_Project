package com.donald.services;

import java.util.ArrayList;
import java.util.List;

import com.donald.pojos.Associate;
import com.donald.pojos.Employee;

public class EmployeeServiceImpl implements EmployeeServiceInt {
	
	// fake will need DAO!
	// just using for testing front to java
	private List<Employee> employeeDAO;

	//will need to change as well - with DAO
	public Employee loginEmployee(String username, String password) {
		Employee employee = null;
		
		for(Employee check : employeeDAO) {
			if(check.getUsername().equals(username) && check.getPassword().equals(password)) {
				employee = check;
				break;
			}
		}
		
		return employee;
	}
	
	
	//fake - delete after test
	public EmployeeServiceImpl() {
		employeeDAO = new ArrayList<Employee>();
		employeeDAO.add(new Associate("ass1", "123", "Associate"));
	}

}
