package com.donald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.ConnectionFactory;
import com.donald.util.LoggingUtil;

public class ReimbursementDAOImpl implements ReimbursementDAOInt {

	private static Connection conn = ConnectionFactory.getConnection();
	private static ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();

	@Override
	public int insertReimbursement(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest) {
		LoggingUtil.debug("insertReimbursement() DAO");

		String sql = "insert into request(employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, time_of_event, description, grading_format, passing_grade)\r\n"
				+ "	values (?,?,?,?,?,to_date(?, 'YYYY-MM-DD'),to_timestamp(?, 'HH24:MI:SS'),?,?,?);";

		PreparedStatement pstmt;
		int numberOfRows = 0;

		try {

			// downcast?

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			// need logic for reimbursementTypeId

			// TODO NEEDS TO BE OUTSIDE METHOD WITH ?SWITCH CASE? STATEMENT
			// would be in EmployeeService i think?

			pstmt.setInt(2, rsi.getEventTypeId(reimbursementRequest.getEventType()));

			pstmt.setInt(3, 1); // always starts UNLESS direct supervisor approval then can go to benco.. will
								// need to figure this logic later? // with new called insert statement()

			pstmt.setInt(4, reimbursementRequest.getCost());
			pstmt.setString(5, reimbursementRequest.getLocationOfEvent());
			pstmt.setString(6, reimbursementRequest.getDateOfEvent()); // dunno if will work!
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

	// get requests for one lower employee
	@Override
	public List<ReimbursementRequest> viewReimbursementRequestForEmployee(Employee loggedInEmployee) {
		LoggingUtil.debug("viewReimbursementRequestForEmployee() DAO");

		List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

		String sql = "select * from request where employee_id in (select e.employee_id\r\n" + "from employee e\r\n"
				+ "inner join employee m \r\n" + "on m.employee_id = e.reports_to\r\n"
				+ "where m.employee_id = ?) and denied = false and approval_reference_id = 1;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
				reimbursementRequest.setId(rs.getInt("request_id"));
				reimbursementRequest.setUserName(getEmployeeUsernameById(rs.getInt("employee_id")));
				reimbursementRequest.setEventType(getReimbursementTypeById(rs.getInt("reimbursement_type_id")));
				reimbursementRequest.setCost(rs.getInt("cost"));
				reimbursementRequest.setLocationOfEvent(rs.getString("location"));
				reimbursementRequest.setDateOfEvent(rs.getString("date_of_event"));
				reimbursementRequest.setTimeOfEvent(rs.getString("time_of_event"));
				reimbursementRequest.setDescription(rs.getString("description"));
				reimbursementRequest.setGradingFormat(rs.getString("grading_format"));
				reimbursementRequest.setPassingGrade(rs.getString("passing_grade"));

				reimbursementRequestList.add(reimbursementRequest);

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return reimbursementRequestList;
	}

	@Override
	public List<ReimbursementRequest> viewReimbursementRequestsDeptHead(Employee loggedInEmployee) {
		LoggingUtil.debug("viewReimbursementRequestsDeptHead() DAO");

		List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

		String sql = "select * from request where employee_id in (select e.employee_id\r\n" + "from employee e\r\n"
				+ "inner join employee m \r\n" + "on m.employee_id = e.reports_to\r\n"
				+ "where m.employee_id in (select m.employee_id\r\n" + "from employee m\r\n"
				+ "inner join employee h\r\n" + "on h.employee_id = m.reports_to\r\n"
				+ "where h.employee_id = ?)) and denied = false and approval_reference_id = 2;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
				reimbursementRequest.setId(rs.getInt("request_id"));
				reimbursementRequest.setUserName(getEmployeeUsernameById(rs.getInt("employee_id")));
				reimbursementRequest.setEventType(getReimbursementTypeById(rs.getInt("reimbursement_type_id")));
				reimbursementRequest.setCost(rs.getInt("cost"));
				reimbursementRequest.setLocationOfEvent(rs.getString("location"));
				reimbursementRequest.setDateOfEvent(rs.getString("date_of_event"));
				reimbursementRequest.setTimeOfEvent(rs.getString("time_of_event"));
				reimbursementRequest.setDescription(rs.getString("description"));
				reimbursementRequest.setGradingFormat(rs.getString("grading_format"));
				reimbursementRequest.setPassingGrade(rs.getString("passing_grade"));

				reimbursementRequestList.add(reimbursementRequest);

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return reimbursementRequestList;
	}

	@Override
	public List<ReimbursementRequest> viewReimbursementRequestsBenCo() {
		LoggingUtil.debug("viewReimbursementRequestsDeptHead() DAO");

		List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

		String sql = "select * from request\r\n" + "where approval_reference_id = 3 and denied = false;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
				reimbursementRequest.setId(rs.getInt("request_id"));
				reimbursementRequest.setUserName(getEmployeeUsernameById(rs.getInt("employee_id")));
				reimbursementRequest.setEventType(getReimbursementTypeById(rs.getInt("reimbursement_type_id")));
				reimbursementRequest.setCost(rs.getInt("cost"));
				reimbursementRequest.setLocationOfEvent(rs.getString("location"));
				reimbursementRequest.setDateOfEvent(rs.getString("date_of_event"));
				reimbursementRequest.setTimeOfEvent(rs.getString("time_of_event"));
				reimbursementRequest.setDescription(rs.getString("description"));
				reimbursementRequest.setGradingFormat(rs.getString("grading_format"));
				reimbursementRequest.setPassingGrade(rs.getString("passing_grade"));

				reimbursementRequestList.add(reimbursementRequest);

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return reimbursementRequestList;
	}

	public String getEmployeeUsernameById(int employee_id) {
		// LoggingUtil.debug("getEmployeeUsernameById() DAO");

		String employeeUsername = null;

		String sql = "select * from employee where employee_id = ?;";

		PreparedStatement pstmt;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, employee_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				employeeUsername = rs.getString("username");
			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return employeeUsername;
	}

	@Override
	public String getReimbursementTypeById(int reimbursement_type_id) {
		// LoggingUtil.debug("getReimbursementTypeById() DAO");

		String reimbursementType = null;
		String sql = "select * from reimbursement_type where reimbursement_type_id = ?;";

		PreparedStatement pstmt;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbursement_type_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				reimbursementType = rs.getString("event_type");
			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return reimbursementType;
	}

	@Override
	public int updateAcceptRequest(int request_id) {
		int numberOfRows = 0;

		String sql = "update request\r\n" + "set approval_reference_id = approval_reference_id + 1\r\n"
				+ "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, request_id);
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateAcceptRequest");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

	@Override
	public int updateDenyRequest(int request_id) {
		int numberOfRows = 0;

		String sql = "update request\r\n" + "set denied = true\r\n" + "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, request_id);
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateDenyRequest");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;

	}

	@Override
	public List<ReimbursementRequest> viewPersonalReimbursementRequests(Employee loggedInEmployee) {
		LoggingUtil.debug("viewPersonalReimbursementRequests() DAO");

		List<ReimbursementRequest> reimbursementRequestList = new ArrayList<>();

		String sql = "select * from request\r\n" + "where employee_id = ? and approval_reference_id = 4;";
		
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
				reimbursementRequest.setId(rs.getInt("request_id"));
				reimbursementRequest.setEventType(getReimbursementTypeById(rs.getInt("reimbursement_type_id")));
				reimbursementRequest.setDateOfEvent(rs.getString("date_of_event"));
				reimbursementRequest.setDescription(rs.getString("description"));
				reimbursementRequest.setGradingFormat(rs.getString("grading_format"));
				reimbursementRequest.setPassingGrade(rs.getString("passing_grade"));

				reimbursementRequestList.add(reimbursementRequest);

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return reimbursementRequestList;
	
	}

}
