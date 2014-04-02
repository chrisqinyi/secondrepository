package org.beeInvestment.model;

public class Reward {
private int status;
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
private Account account;
private Investment investment;
public Account getAccount() {
	return account;
}
public void setAccount(Account account) {
	this.account = account;
}
public Investment getInvestment() {
	return investment;
}
public void setInvestment(Investment investment) {
	this.investment = investment;
}
}
