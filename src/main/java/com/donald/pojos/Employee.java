package com.donald.pojos;

public abstract class Employee {
	
	private String username;
	private String password;
	private String employeeType;
	
	public Employee(String username, String password, String employeeType) {
		super();
		this.username = username;
		this.password = password;
		this.employeeType = employeeType;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	@Override
	public String toString() {
		return "Employee [username=" + username + ", password=" + password + "]";
	}
	
	
	

}
