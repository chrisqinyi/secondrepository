package org.beeInvestment.model;

import java.math.BigDecimal;

public class Deposit {
	private BigDecimal amount;

	public Deposit(BigDecimal bigDecimal) {
		amount=bigDecimal;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	

}
