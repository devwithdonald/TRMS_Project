package com.donald.services;

import java.time.Duration;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.util.LoggingUtil;

public class ValidationServiceImpl implements ValidationServiceInt {

	ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();

	@Override
	public boolean balanceVerification(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest) {

		if (loggedInEmployee.getAvailableBalance() - loggedInEmployee.getPendingBalance()
				- rsi.calculateAwardByReimbursementType(reimbursementRequest.getEventType(), reimbursementRequest.getCost()) < 0) {
			//balance is not okay
			return false;
		} else {
			// balance is okay
			return true;
		}

	}
	
	@Override
	public boolean dateCheck(String date) {

		// DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate currentDate = LocalDate.now();

		LocalDate checkDate = LocalDate.parse(date);
		Duration diff = Duration.between(currentDate.atStartOfDay(), checkDate.atStartOfDay());

		long diffDays = diff.toDays();

		LoggingUtil.debug("diff days" + diffDays);

		// should check diff days is also positive
		if (diffDays > 7) {

			LoggingUtil.debug("date verified");
			return true;
		} else {
			LoggingUtil.debug("date un-verified");
		}
		return false;

	}


}
