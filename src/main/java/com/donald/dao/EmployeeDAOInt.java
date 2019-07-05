package com.donald.dao;

import java.util.List;

import com.donald.pojos.Employee;

public interface EmployeeDAOInt {

	public List<Employee> getAllEmployees();

	public Employee getEmployeeById(int employeeId);

	public int updateEmployeePendingBalance(Employee employee, int amount);

	public int updateAvailableBalance(Employee employee, int amount);

}
