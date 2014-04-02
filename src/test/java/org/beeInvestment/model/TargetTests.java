package org.beeInvestment.model;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TargetTests extends BaseTestCase {
	@Test
	public void publishTarget(){
		Map criteria = null;
		List<Target> targets=customer.getTargetList(criteria);
	user.publishTarget(new Target());	
	customer.getTargetList(criteria);
	}
	@Test
	public void getTargetList(){
		Map criteria = null;
		List<Target> targets=customer.getTargetList(criteria);
	}
	@Test
	public void approveTarget(){
		user.approveTarget(new Target());
	}
	

}
