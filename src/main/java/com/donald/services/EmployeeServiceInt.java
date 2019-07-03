package com.donald.services;

import com.donald.pojos.Employee;

public interface EmployeeServiceInt {
	
	public Employee loginEmployee(String username, String password);
	
	public void awardPendingReward(int requestId, int awardAmount);
	


}
