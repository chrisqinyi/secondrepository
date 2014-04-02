package org.beeInvestment.model;

import org.junit.Test;

public class InvestmentTests extends BaseTestCase {
	@Test
	public void makeInvetment() {
		customer.getTargetList(null);
		Target target = null;
		
		Investment investment = customer.invest(target);

	}

	@Test
	public void customerGetInvetmentList() {
		
		customer.getAccount().getInvestments();
	}
	

}
