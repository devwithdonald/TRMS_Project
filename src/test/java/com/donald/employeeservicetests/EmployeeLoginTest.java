//package com.donald.employeeservicetests;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.donald.pojos.Employee;
//import com.donald.services.EmployeeServiceImpl;
//
//public class EmployeeLoginTest {
//
//	private static EmployeeServiceImpl esi;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		esi = new EmployeeServiceImpl();
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
//	public void testAssociateInDB() {
//		Employee returnedEmployee = esi.loginEmployee("test", "123");
//
//		Assert.assertNotNull(returnedEmployee);
//
//		Assert.assertEquals(true, returnedEmployee instanceof Employee);
//	}
//	
//	@Test
//	public void testSupervisorInDB() {
//		Employee returnedEmployee = esi.loginEmployee("supervisor", "456");
//
//		Assert.assertNotNull(returnedEmployee);
//
//		Assert.assertEquals(true, returnedEmployee instanceof Employee);
//	}
//	
//	@Test
//	public void testDeptHeadInDB() {
//		Employee returnedEmployee = esi.loginEmployee("department head", "789");
//
//		Assert.assertNotNull(returnedEmployee);
//
//		Assert.assertEquals(true, returnedEmployee instanceof Employee);
//	}
//	
//	@Test
//	public void testBenCoInDB() {
//		Employee returnedEmployee = esi.loginEmployee("benco", "321");
//
//		Assert.assertNotNull(returnedEmployee);
//
//		Assert.assertEquals(true, returnedEmployee instanceof Employee);
//	}
//	
//	@Test
//	public void testEmployeeNOTInDB() {
//		Employee returnedEmployee = esi.loginEmployee("", "");
//
//		Assert.assertNull(returnedEmployee);
//
//		Assert.assertEquals(false, returnedEmployee instanceof Employee);
//	}
//	
//
//}
