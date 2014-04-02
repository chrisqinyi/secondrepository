package org.beeInvestment.repository;

import org.beeInvestment.model.Credential;
import org.beeInvestment.model.Customer;

public interface CustomerRepository extends Repository<Customer>{
	Customer getByCredential(Credential credential);
}