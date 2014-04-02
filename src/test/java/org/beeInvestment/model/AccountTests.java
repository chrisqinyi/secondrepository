package org.beeInvestment.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class AccountTests extends BaseTestCase {
	@Test
	public void AccountSummary() {
		Credential credential = testHelper.getValidCredential();
		Customer customer = bee.authenticate(credential);
		Account account=customer.getAccount();
		
        assertNotNull(account);
	}

	@Test
	public void deplosit() {
		Account account = customer.getAccount();
		account.setBalance(BigDecimal.valueOf(5000));
		BigDecimal balance = account.getBalance();
		Deposit deposit=customer.deposit(new Deposit(new BigDecimal(5000)));
		assertTrue("Account balane should increase properly after deposity transaction",5000 == customer.getAccount().getBalance().doubleValue()-balance.doubleValue());

	}

	@Test
	public void applyForWithdraw() {
		Withdraw withdraw=customer.applyForWithdraw(new Withdraw(new BigDecimal(5000)));
	}
	@Test
	public void approveWithdraw() {
		
		List<Withdraw> withdraws= user.getWithdrawList(null);
		user.approveWithdraw(new Withdraw(new BigDecimal(5000)));
		Account account=user.getAccount(customer);
		customer.getAccount();
	}
	@Test
	public void viewInvestmentAccount() {
		Map criteria=null;
		Account account=user.getAccount(criteria);
	}

	@Test
	public void freezeAccount() {
		Map criteria=null;
		Account account=user.getAccount(criteria);
		user.freezeAccount(account);
	}

	@Test
	public void getInvestAccountList() {
		Map criteria = null;
		List<Account> accounts=user.getAccountList(criteria);
	}

}
