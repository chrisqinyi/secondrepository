package org.beeInvestment.model;

import java.util.List;

import org.junit.Test;

public class CreditAssignmentTests extends BaseTestCase{
	@Test
	public void offerInvestment() {
		Investment originalInvestment=null;
		CreditAssignment assignment=customer.offerInvestment(originalInvestment);
	}

	@Test
	public void acceptInvestment() {
		
		List<CreditAssignment> creditAssignments= customer.getCreditAssignmentList();
		CreditAssignment assignment=null;
		Investment newInvestment=customer.acceptInvestment(assignment);
	}
}
