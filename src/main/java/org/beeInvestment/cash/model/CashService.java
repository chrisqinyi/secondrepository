package org.beeInvestment.cash.model;

import java.util.Collection;
import java.util.Map;

public interface CashService {
	Deposit deposit(Map requestMap);
	void acknowledgeDeposit(Map requestMap);
	Collection<Deposit> depositHistory(Map requestMap);
}
