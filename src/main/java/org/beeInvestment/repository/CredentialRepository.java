package org.beeInvestment.repository;

import org.beeInvestment.model.Credential;
import org.beeInvestment.model.Customer;

public interface CredentialRepository extends Repository<Credential>{

	Credential getDefaultCredential(Customer newCustomer);

}