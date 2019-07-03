package com.donald.services;

import com.donald.dao.EmployeeDAOImpl;
import com.donald.dao.ReimbursementDAOImpl;
import com.donald.pojos.Employee;
import com.donald.util.LoggingUtil;

public class EmployeeServiceImpl implements EmployeeServiceInt {
	
	private static ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	private static EmployeeDAOImpl edi = new EmployeeDAOImpl();
	private static ReimbursementDAOImpl rdi = new ReimbursementDAOImpl();


	public Employee loginEmployee(String username, String password) {
		Employee employee = null;
		
		//d o it here
		for(Employee check : edi.getAllEmployees()) {
			if(check.getUsername().equals(username) && check.getPassword().equals(password)) {
				employee = check;
				break;
			}
		}
		
		//need to populate employee with balances?
		
		return employee;
	}


	@Override
	public void awardPendingReward(int requestId, int awardAmount) {
		// call dao
		// need to make balance reflect new amount now!
		
		//turn awardAmount negative.
		//need to set pending balance minus the awardAmount

		int employeeId = rdi.getEmployeeIdByRequestId(requestId);
		
		LoggingUtil.debug("employee Id ------> " + employeeId);
		Employee employee = edi.getEmployeeById(employeeId);
		
		//turn awardAmount negative
		//awardAmount *= -1;
		
		//cancel out the award for pending balance
		//edi.updateEmployeePendingBalance(employee, -awardAmount);
		//edi.updateBalance(employeeId, -awardAmount);
		
		edi.updateEmployeePendingBalance(employee, -awardAmount);
		edi.updateAvailableBalance(employee, -awardAmount);
		
	}


	@Override
	public void denyPendingReward(int requestId) {

		int amount = rsi.calculateAward(requestId);
		int employeeId = rdi.getEmployeeIdByRequestId(requestId);
		LoggingUtil.debug("employee Id ------> " + employeeId);
		Employee employee = edi.getEmployeeById(employeeId);
		//cancel out amount so negative
		edi.updateEmployeePendingBalance(employee, -amount);
		//set to plus award amount
		//edi.updateAvailableBalance(employee, amount);
		
	}
	


}
