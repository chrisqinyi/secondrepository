package org.beeInvestment.model;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class UserMaintenanceTests extends BaseTestCase {
	@Test
	public void userRegistration() {
		Customer newCustomer=bee.createCustomer(testHelper.getValidRegisterForm());
		assertNotNull("Newly registered customer should not be null",newCustomer);
		Credential credential=bee.getDefaultCredential(newCustomer);
		Customer customer=bee.authenticate(credential);
		assertNotNull("Authenticated customer should not be null",customer);
		assertTrue("Authenticated customer should equals to new customer",customer.equals(newCustomer));
	}

	@Test
	public void userLogin() {
		String username = null,password = null;
		Credential credential=testHelper.getValidCredential();
		//customer.login(username,password);
		Customer customer=bee.authenticate(credential);
		assertNotNull("Authenticated customer should not be null",customer);
	}
	@Test
	public void userLoginFailed() {
		String username = null,password = null;
		Credential credential=testHelper.getInvalidCredential();
		//customer.login(username,password);
		Customer customer=bee.authenticate(credential);
		assertNull("Authenticated customer should be null",customer);
	}
	@Test
	public void passwordChange() {
		//String oldPassword = null,newPassword = null;
		Credential credential=testHelper.getValidCredential();
		customer.updateCredential(credential);
		Credential credential1=bee.getDefaultCredential(customer);
		Customer customer=bee.authenticate(credential);
		assertNull("Authenticated customer using old credential should be null",customer);
		Customer customer1=bee.authenticate(credential1);
		assertNotNull("Authenticated customer should not be null",customer1);
		
		//Credential credential=new Credential();
	}

	@Test
	public void identityAuthentication() {
		Map criteria = null;
		user.getCustomerList(criteria);
		user.viewCustomer(new Customer());
		user.authenticate(customer);
	}

	@Test
	public void setupSecurityQuestions() {
		Credential credential=new Credential();
		//CredentialHistory credentialHistory=customer.updateCredential(credential);
		Credential credential1=new Credential();
		bee.authenticate(credential1);
	}

	@Test
	public void mobileBinding() {
		Profile profile=new Profile();
		customer.updateProfile(profile);
	}

	@Test
	public void porfileEdit() {
		Profile profile=new Profile();
		customer.updateProfile(profile);
	}

}
