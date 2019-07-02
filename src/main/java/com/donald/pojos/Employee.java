package com.donald.pojos;

public abstract class Employee {
	
	private Integer employeeId;
	private String username;
	private String password;
	private String employeeType;
	private int availableBalance;
	
	public Employee(Integer employeeId, String username, String password, String employeeType) {
		super();
		this.employeeId = employeeId;
		this.username = username;
		this.password = password;
		this.employeeType = employeeType;
	}
	
	public int getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(int availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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
