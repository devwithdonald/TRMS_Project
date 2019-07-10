//package com.donald.employeeservicetests;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.donald.pojos.Associate;
//import com.donald.pojos.Employee;
//import com.donald.services.ReimbursementServiceImpl;
//
//public class ReimbursementFormRequestTest {
//	
//	private static ReimbursementServiceImpl rsi;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		rsi = new ReimbursementServiceImpl();
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//	
//	@Test
//	public void insertNewRequestTest() {
//		// fake logging in an employee
//		//Employee loggedInEmployee = new Associate();
//	}
//
//	@Test
//	public void eventIdValidationTest1() {
//		int testId = rsi.getEventTypeId("University Course");
//		Assert.assertEquals(1, testId);
//	}
//	@Test
//	public void eventIdValidationTest2() {
//		int testId = rsi.getEventTypeId("Seminar");
//		Assert.assertEquals(2, testId);
//	}
//	@Test
//	public void eventIdValidationTest3() {
//		int testId = rsi.getEventTypeId("Certification Preparation Classes");
//		Assert.assertEquals(3, testId);
//	}
//	@Test
//	public void eventIdValidationTest4() {
//		int testId = rsi.getEventTypeId("Certification");
//		Assert.assertEquals(4, testId);
//	}
//	@Test
//	public void eventIdValidationTest5() {
//		int testId = rsi.getEventTypeId("Technical Training");
//		Assert.assertEquals(5, testId);
//	}
//	@Test
//	public void eventIdValidationTest6() {
//		int testId = rsi.getEventTypeId("Other");
//		Assert.assertEquals(6, testId);
//	}
//
//}
