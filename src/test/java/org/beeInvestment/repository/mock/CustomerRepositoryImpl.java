package org.beeInvestment.repository.mock;

import java.util.Collection;
import java.util.Map;

import org.beeInvestment.model.Credential;
import org.beeInvestment.model.Customer;
import org.beeInvestment.model.TestHelper;
import org.beeInvestment.model.TestValidCredential;
import org.beeInvestment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerRepositoryImpl implements CustomerRepository {
	@Autowired
	private Customer customer;

	@Autowired
	private TestHelper testHelper;

	@Override
	public Customer findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getByCredential(Credential credential) {
		return testHelper.getCustomerByCredential(credential);
	}


	public void save(Customer t) {
		t.setId(customer.getId());
		
	}

	@Override
	public Collection<Customer> find(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return null;
	}





}
