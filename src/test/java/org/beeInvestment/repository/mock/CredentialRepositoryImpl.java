package org.beeInvestment.repository.mock;

import java.util.Collection;
import java.util.Map;

import org.beeInvestment.model.Credential;
import org.beeInvestment.model.Customer;
import org.beeInvestment.model.TestHelper;
import org.beeInvestment.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class CredentialRepositoryImpl implements CredentialRepository {
	@Autowired
	private Customer customer;

	@Autowired
	private TestHelper testHelper;
	@Override
	public void save(Credential t) {
		t=testHelper.getInvalidCredential();
	}


	@Override
	public Credential findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<Credential> find(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Credential getDefaultCredential(Customer newCustomer) {
		// TODO Auto-generated method stub
		return testHelper.getValidCredential();
	}

}
