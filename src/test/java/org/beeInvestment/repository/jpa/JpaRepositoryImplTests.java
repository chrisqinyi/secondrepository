package org.beeInvestment.repository.jpa;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/business-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jpa")
public class JpaRepositoryImplTests {
	@Autowired
	private org.beeInvestment.repository.Repository jpaRepositoryOwnerOwner;
	
    @Test
    public void findSingleOwner() {
//    	Owner findById = (Owner) jpaRepositoryOwnerOwner.findById(1);
//    	assertEquals("Franklin", findById.getLastName());
    }
    @Test
    public void findSingleOwner1() {
//    	Collection find = jpaRepositoryOwnerOwner.find(new HashMap());
//    	Owner owner=(Owner) find.iterator().next();
//    	assertEquals("Franklin", owner.getLastName());
    }
    @Test
    public void findSingleOwner2() {
//    	HashMap parameterMap = new HashMap();
//    	parameterMap.put("id", 1);
//		Collection find = jpaRepositoryOwnerOwner.find(parameterMap);
//		Owner owner=(Owner) find.iterator().next();
//    	assertEquals("Franklin", owner.getLastName());
    }
}
