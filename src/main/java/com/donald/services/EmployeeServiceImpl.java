package com.donald.services;

import com.donald.dao.EmployeeDAOImpl;
import com.donald.pojos.Employee;

public class EmployeeServiceImpl implements EmployeeServiceInt {
	

	private static EmployeeDAOImpl edi = new EmployeeDAOImpl();


	public Employee loginEmployee(String username, String password) {
		Employee employee = null;
		
		for(Employee check : edi.getAllEmployees()) {
			if(check.getUsername().equals(username) && check.getPassword().equals(password)) {
				employee = check;
				break;
			}
		}
		
		return employee;
	}
	


}
