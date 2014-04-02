package org.beeInvestment.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class Account extends BaseEntity{
	private BigDecimal balance;
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public List<Investment> getInvestments() {
		return investments;
	}
	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}
	public List<Reward> getRewards() {
		return rewards;
	}
	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}
	public List<Deposit> getDeposits() {
		return deposits;
	}
	public void setDeposits(List<Deposit> deposits) {
		this.deposits = deposits;
	}
	public List<Withdraw> getWithdraws() {
		return withdraws;
	}
	public void setWithdraws(List<Withdraw> withdraws) {
		this.withdraws = withdraws;
	}
	public List<AccountHistory> getAccountHistories() {
		return accountHistories;
	}
	public void setAccountHistories(List<AccountHistory> accountHistories) {
		this.accountHistories = accountHistories;
	}
	private List<Investment> investments=new ArrayList<Investment>();
	private List<Reward> rewards=new ArrayList<Reward>();
	private List<Deposit> deposits=new ArrayList<Deposit>();
	private List<Withdraw> withdraws=new ArrayList<Withdraw>();
	private List<AccountHistory> accountHistories=new ArrayList<AccountHistory>();
}
