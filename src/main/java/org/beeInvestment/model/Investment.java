package org.beeInvestment.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Investment {
	private BigDecimal totalAmount;
	private BigDecimal remainingAmount;
	private double interestedRate;
	private List<InvestmentHistory> investmentHistories=new ArrayList<InvestmentHistory>();
}
