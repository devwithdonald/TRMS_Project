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

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			pstmt.setInt(2, rsi.getEventTypeId(reimbursementRequest.getEventType()));
			pstmt.setInt(3, 1);
			pstmt.setInt(4, reimbursementRequest.getCost());
			pstmt.setString(5, reimbursementRequest.getLocationOfEvent());
			pstmt.setString(6, reimbursementRequest.getDateOfEvent());
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
				+ "where m.employee_id = ?) and denied = false and approval_reference_id = 1 and need_additional_info = false;";

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
				+ "where h.employee_id = ?)) and denied = false and approval_reference_id = 2 and need_additional_info = false;";

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

		String sql = "select * from request\r\n"
				+ "where approval_reference_id = 3 and denied = false and need_additional_info = false;;";

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

	public String getEmployeeUsernameById(int employeeId) {

		String employeeUsername = null;

		String sql = "select * from employee where employee_id = ?;";

		PreparedStatement pstmt;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, employeeId);
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
	public String getReimbursementTypeById(int reimbursementTypeId) {

		String reimbursementType = null;
		String sql = "select * from reimbursement_type where reimbursement_type_id = ?;";

		PreparedStatement pstmt;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbursementTypeId);
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
	public int updateAcceptRequest(int requestId) {
		int numberOfRows = 0;

		String sql = "update request\r\n" + "set approval_reference_id = approval_reference_id + 1\r\n"
				+ "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, requestId);
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateAcceptRequest");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

	@Override
	public int updateDenyRequest(int requestId) {
		int numberOfRows = 0;

		String sql = "update request\r\n" + "set denied = true\r\n" + "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, requestId);
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

	@Override
	public int updateGradeRequest(int requestId, String grade) {

		int numberOfRows = 0;

		String sql = "update request\r\n" + "set grade_received = ?, approval_reference_id = 5\r\n"
				+ "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, grade);
			pstmt.setInt(2, requestId);
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateGradeRequest");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

	@Override
	public List<ReimbursementRequest> viewGradedRequests() {
		LoggingUtil.debug("viewGradedRequests() DAO");

		List<ReimbursementRequest> gradedRequestList = new ArrayList<>();

		String sql = "select * from request\r\n"
				+ "where approval_reference_id = 5 and denied = false and award_given = false;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
				reimbursementRequest.setId(rs.getInt("request_id"));
				reimbursementRequest.setUserName(getEmployeeUsernameById(rs.getInt("employee_id")));
				reimbursementRequest.setEventType(getReimbursementTypeById(rs.getInt("reimbursement_type_id")));
				reimbursementRequest.setGradingFormat(rs.getString("grading_format"));
				reimbursementRequest.setPassingGrade(rs.getString("passing_grade"));
				reimbursementRequest.setGradeReceived(rs.getString("grade_received"));

				gradedRequestList.add(reimbursementRequest);

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return gradedRequestList;
	}

	@Override
	public int updateFinalGrade(int requestId, boolean denied, boolean award_given) {
		int numberOfRows = 0;

		String sql = "update request\r\n" + "set denied = ?, award_given = ?\r\n" + "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setBoolean(1, denied);
			pstmt.setBoolean(2, award_given);
			pstmt.setInt(3, requestId);
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateAcceptRequest");

		} catch (SQLException e) {

		}

		return numberOfRows;
	}

	@Override
	public int getEmployeeIdByRequestId(int requestId) {
		LoggingUtil.debug("in getEmployeeIdByRequestId method()");

		int employeeId = 0;

		String sql = "select employee_id from request\r\n" + "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, requestId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				employeeId = rs.getInt("employee_id");
			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}
		return employeeId;
	}

	@Override
	public ReimbursementRequest getReimbursementRequest(int requestId) {
		ReimbursementRequest reimbursementRequest = null;

		String sql = "select * from request\r\n" + "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, requestId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				reimbursementRequest = new ReimbursementRequest();
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

			}
		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}
		return reimbursementRequest;
	}

	@Override
	public int getReimbursementPaybackPercentageByReimbursementType(String ReimbursementRequestType) {

		int paybackPercentage = 0;

		String sql = "select payback_percentage from reimbursement_type\r\n" + "where event_type = ?;";
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ReimbursementRequestType);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				paybackPercentage = rs.getInt("payback_percentage");
			}
		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return paybackPercentage;
	}

	@Override
	public int insertReimbursementAward(String employeeUsername, int reimbursementTypeId, int awardAmount) {
		LoggingUtil.debug("insertReimbursementAward() DAO");

		String sql = "insert into reimbursement_award(employee_id, reimbursement_type_id, amount_gifted)\r\n"
				+ "	values(?,?,?);";

		PreparedStatement pstmt;

		int numberOfRows = 0;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getEmployeeIdByUsername(employeeUsername));
			pstmt.setInt(2, reimbursementTypeId);
			pstmt.setInt(3, awardAmount);

			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - insertReimbursement()");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

	@Override
	public int getEmployeeIdByUsername(String userName) {

		int employeeId = 0;

		String sql = "select employee_id from employee\r\n" + "where username = ?;";

		PreparedStatement pstmt;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				employeeId = rs.getInt("employee_id");
			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return employeeId;
	}

	@Override
	public int updateRequestAdditionalInfo(int requestId, String additionalInfo) {
		int numberOfRows = 0;

		String sql = "update request \r\n" + "set need_additional_info = true, requested_additional_info = ?\r\n"
				+ "where request_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, additionalInfo);
			pstmt.setInt(2, requestId);
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateRequestAdditionalInfo");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

	@Override
	public List<ReimbursementRequest> getAdditionalInformationRequests(Employee loggedInEmployee) {
		LoggingUtil.debug("getAdditionalInformationRequests() DAO");

		List<ReimbursementRequest> additionalInfoRequestList = new ArrayList<>();

		String sql = "select * from request \r\n" + "where need_additional_info = true and employee_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loggedInEmployee.getEmployeeId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
				reimbursementRequest.setId(rs.getInt("request_id"));
				reimbursementRequest.setRequestedAdditionalInfo(rs.getString("requested_additional_info"));
				reimbursementRequest.setEventType(getReimbursementTypeById(rs.getInt("reimbursement_type_id")));
				reimbursementRequest.setDescription(rs.getString("description"));
				reimbursementRequest.setGradingFormat(rs.getString("grading_format"));

				additionalInfoRequestList.add(reimbursementRequest);

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return additionalInfoRequestList;
	}

	@Override
	public int updateAdditionalInformationResponse(int requestId, String responseMessage, Employee loggedInEmployee) {
		int numberOfRows = 0;

		String sql = "update request\r\n"
				+ "set need_additional_info = false, description = description || '--req info-- ' || ?\r\n"
				+ "where request_id = ? and employee_id = ? and need_additional_info = true;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, responseMessage);
			pstmt.setInt(2, requestId);
			pstmt.setInt(3, loggedInEmployee.getEmployeeId());
			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateAdditionalInformationResponse");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}
		return numberOfRows;
	}

}
