package org.beeInvestment.cash.repository;

import java.util.Collection;

public interface DepositRepository {
	DepositVO getById(int id);
	Collection<DepositVO> find(DepositVO depositVO);
	void save(DepositVO depositVO);
	
}
