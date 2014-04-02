package org.beeInvestment.model;

import org.junit.Test;

public class RewardTests extends BaseTestCase {
	@Test
	public void reward() {
		Reward reward = new Reward();
		user.reward(reward);
		reward.getAccount();
		reward.getInvestment();
	}

	@Test
	public void getRewardList() {
		customer.getAccount().getRewards();
	}
}
