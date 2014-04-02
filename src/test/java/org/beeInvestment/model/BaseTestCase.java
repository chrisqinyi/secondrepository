package org.beeInvestment.model;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/business-config.xml",
		"classpath:spring/abstractSessionTest.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "model","mock" })
public class BaseTestCase {
	@Autowired
	protected Customer customer;
	@Autowired
	protected User user;
	@Autowired
	protected Bee bee;

	@Before
	public void setup() {
		customer.setAccount(new Account());
	}
	@Autowired
	protected TestHelper testHelper;
}
