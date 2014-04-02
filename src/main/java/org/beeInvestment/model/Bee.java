package org.beeInvestment.model;

import org.beeInvestment.repository.CredentialRepository;
import org.beeInvestment.repository.CustomerRepository;
import org.beeInvestment.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

public class Bee {
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private CustomerRepository customerRepository;
	public Customer authenticate(Credential credential) {
		return customerRepository.getByCredential(credential);
	}

	public Customer createCustomer(RegisterForm registerForm) {
		Customer customer=new Customer();
		customerRepository.save(customer);
		Credential credential=new Credential();
		credentialRepository.save(credential);
		return customer;
	}

	public Credential getDefaultCredential(Customer newCustomer) {
		// TODO Auto-generated method stub
		return credentialRepository.getDefaultCredential(newCustomer);
	}

}
