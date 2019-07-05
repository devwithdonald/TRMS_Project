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

		for (Employee check : edi.getAllEmployees()) {
			if (check.getUsername().equals(username) && check.getPassword().equals(password)) {
				employee = check;
				break;
			}
		}

		return employee;
	}

	@Override
	public void awardPendingReward(int requestId, int awardAmount) {

		int employeeId = rdi.getEmployeeIdByRequestId(requestId);

		LoggingUtil.debug("employee Id ------> " + employeeId);
		Employee employee = edi.getEmployeeById(employeeId);

		edi.updateEmployeePendingBalance(employee, -awardAmount);
		edi.updateAvailableBalance(employee, -awardAmount);

	}

	@Override
	public void denyPendingReward(int requestId) {

		int amount = rsi.calculateAward(requestId);
		int employeeId = rdi.getEmployeeIdByRequestId(requestId);
		LoggingUtil.debug("employee Id ------> " + employeeId);
		Employee employee = edi.getEmployeeById(employeeId);
		// cancel out amount so negative
		edi.updateEmployeePendingBalance(employee, -amount);

	}

}
