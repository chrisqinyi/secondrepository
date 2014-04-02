package org.beeInvestment.cash.repository;

import java.math.BigDecimal;

public class DepositVO {
	DepositVO(){}
private BigDecimal amount;

public BigDecimal getAmount() {
	return amount;
}

public void setAmount(BigDecimal amount) {
	this.amount = amount;
}
}
