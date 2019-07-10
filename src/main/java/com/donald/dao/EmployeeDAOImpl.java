package com.donald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.donald.pojos.Associate;
import com.donald.pojos.BenefitsCoordinator;
import com.donald.pojos.DepartmentHead;
import com.donald.pojos.Employee;
import com.donald.pojos.Supervisor;
import com.donald.util.ConnectionFactory;
import com.donald.util.LoggingUtil;

public class EmployeeDAOImpl implements EmployeeDAOInt {

	private static Connection conn = ConnectionFactory.getConnection();

	@Override
	public List<Employee> getAllEmployees() {
		LoggingUtil.trace("In getAllEmployees() method");

		List<Employee> employeeList = new ArrayList<>();
		Employee employee = null;

		//join to get employee type!
		String sql = "select emp.*, emp_type.employee_type " + "from employee emp "
				+ "inner join employee_type emp_type on emp.employee_type_id = emp_type.employee_type_id;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				if (rs.getInt("employee_type_id") == 1) {
					employee = new Associate(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), rs.getString("employee_type"), rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				} else if (rs.getInt("employee_type_id") == 2) {
					employee = new Supervisor(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), rs.getString("employee_type"), rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				} else if (rs.getInt("employee_type_id") == 3) {
					employee = new DepartmentHead(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), rs.getString("employee_type"), rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				} else if (rs.getInt("employee_type_id") == 4) {
					employee = new BenefitsCoordinator(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), rs.getString("employee_type"), rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				}

				employeeList.add(employee);
			}
		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return employeeList;
	}

	@Override
	public int updateEmployeePendingBalance(Employee employee, int amount) {
		LoggingUtil.debug("In updateEmployeePendingBalance() method");

		int numberOfRows = 0;

		String sql = "update employee\r\n" + "set pending_balance = pending_balance + ?\r\n" + "where employee_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, employee.getEmployeeId());

			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateEmployeePendingBalance");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		LoggingUtil.debug("In getEmployeeById() method");
		Employee employee = null;

		String sql = "select * from employee\r\n" + "where employee_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, employeeId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				if (rs.getInt("employee_type_id") == 1) {
					employee = new Associate(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), "associate", rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				} else if (rs.getInt("employee_type_id") == 2) {
					employee = new Supervisor(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), "supervisor", rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				} else if (rs.getInt("employee_type_id") == 3) {
					employee = new DepartmentHead(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), "department head", rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				} else if (rs.getInt("employee_type_id") == 4) {
					employee = new BenefitsCoordinator(rs.getInt("employee_id"), rs.getString("username"),
							rs.getString("password"), "benefits coordinator", rs.getInt("available_balance"));
					employee.setPendingBalance(rs.getInt("pending_balance"));
				}

			}

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return employee;
	}

	@Override
	public int updateAvailableBalance(Employee employee, int amount) {
		LoggingUtil.debug("In updateBalance() method");

		int numberOfRows = 0;

		String sql = "update employee\r\n" + "set available_balance = available_balance + ?\r\n"
				+ "where employee_id = ?;";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, employee.getEmployeeId());

			numberOfRows = pstmt.executeUpdate();

			LoggingUtil.debug(numberOfRows + " number of rows affected - updateAvailableBalance");

		} catch (SQLException e) {
			LoggingUtil.error(e.getMessage());
		}

		return numberOfRows;
	}

}
