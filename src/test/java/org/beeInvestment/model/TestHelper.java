package org.beeInvestment.model;

public class TestHelper {
	
	private Credential validCredential=new Credential();
	
	public Credential getValidCredential() {
		return validCredential=new Credential() ;
	}

	public Credential getInvalidCredential() {
		return new TestInvalidCredential();
	}
	
	public RegisterForm getValidRegisterForm(){
		return null;
	}
	public RegisterForm getInvalidRegisterForm(){
		return null;
	}
	public Customer getCustomerByCredential(Credential credential){
		if(credential==validCredential){
			return new Customer();
		}else{
			return null;
		}
	}
}

