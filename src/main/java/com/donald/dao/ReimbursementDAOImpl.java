package com.donald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.ConnectionFactory;
import com.donald.util.LoggingUtil;

public class ReimbursementDAOImpl implements ReimbursementDAOInt{
	
	private static Connection conn = ConnectionFactory.getConnection();
	private static ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();

	@Override
	public int insertReimbursement(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest) {
		LoggingUtil.debug("insertRebursement()");
		
		String sql = "insert into request(employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, time_of_event, description, grading_format, passing_grade)\r\n" + 
				"	values (?,?,?,?,?,to_date(?, 'YYYY-MM-DD'),to_timestamp(?, 'HH24:MI:SS'),?,?,?);";
		
		PreparedStatement pstmt;
		int numberOfRows = 0;
		
		try {
			
			//downcast?
			 
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			//need logic for reimbursementTypeId
			
			//TODO NEEDS TO BE OUTSIDE METHOD WITH ?SWITCH CASE? STATEMENT
			//would be in EmployeeService i think?
			
			pstmt.setInt(2, rsi.getEventTypeId(reimbursementRequest.getEventType()));
			
			pstmt.setInt(3,1); //always starts UNLESS direct supervisor approval then can go to benco.. will need to figure this logic later? // with new called insert statement()
			
			pstmt.setInt(4, reimbursementRequest.getCost());
			pstmt.setString(5, reimbursementRequest.getLocationOfEvent());
			pstmt.setString(6, reimbursementRequest.getDateOfEvent()); //dunno if will work!
			pstmt.setString(7, reimbursementRequest.getTimeOfEvent());
			pstmt.setString(8, reimbursementRequest.getDescription());
			pstmt.setString(9, reimbursementRequest.getGradingFormat());
			pstmt.setString(10, reimbursementRequest.getPassingGrade());
			
			numberOfRows = pstmt.executeUpdate();
			
			LoggingUtil.debug(numberOfRows + " number of rows affected - insertReimbursement()");
			
		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}
		
		return numberOfRows;
	}
}
