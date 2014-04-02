package org.beeInvestment.model;

import java.util.List;
import java.util.Map;

import org.beeInvestment.repository.CredentialRepository;
import org.beeInvestment.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

public class Customer extends org.beeInvestment.model.BaseEntity {
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private Repository<Audit> auditRepository;
	private boolean authenticated;
	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	@Autowired
	private Account account;
	@Autowired
	private Profile profile;


	public CreditAssignment offerInvestment(Investment originalInvestment) {
		// TODO Auto-generated method stub
		return null;
	}

	public org.beeInvestment.model.Investment acceptInvestment(
			CreditAssignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	public Deposit deposit(Deposit deposit) {
		this.getAccount().setBalance(this.getAccount().getBalance().add(deposit.getAmount()));
		return deposit;
	}

	public Withdraw applyForWithdraw(Withdraw withdraw) {
		// TODO Auto-generated method stub
		return null;
	}

	public Investment invest(Target target) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Target> getTargetList(Map criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	public Audit updateCredential(Credential credential) {
		credentialRepository.save(credential);
		Audit t = new Audit();
		auditRepository.save(t);
		return t;
	}

	public List<CreditAssignment> getCreditAssignmentList() {
		// TODO Auto-generated method stub
		return null;
	}

}
