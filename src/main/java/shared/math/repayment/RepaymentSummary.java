package shared.math.repayment;

import java.util.List;

public class RepaymentSummary {

	private boolean principalAndInterest;
	private double initialLoanAmount;
	private double periodicPayment;
	private double totalInterest;
	private double periodicRate;
	private double closingBalance;
	private int totalTerm;

	private List<PaymentAmount> payments;

	public RepaymentSummary(boolean principalAndInterest,
							double totalLoanAmount,
							double periodicPayment,
							double totalInterest,
							double periodicRate,
							int totalTerm,
							double closingBalance,
							List<PaymentAmount> payments) {
		
		this.principalAndInterest = principalAndInterest;
		this.initialLoanAmount = totalLoanAmount;
		this.periodicPayment = periodicPayment;
		this.totalInterest = totalInterest;
		this.periodicRate = periodicRate;
		this.totalTerm = totalTerm;
		this.closingBalance = closingBalance;
		this.payments = payments;
	}
	
	public boolean isPrincipalAndInterest() {
		return principalAndInterest;
	}
	
	public double getInitialLoanAmount() {
		return initialLoanAmount;
	}

	public double getPeriodicPayment() {
		return periodicPayment;
	}
	
	public double getTotalInterest() {
		return totalInterest;
	}

	public double getPeriodicRate() {
		return periodicRate;
	}

	public int getTotalTerm() {
		return totalTerm;
	}
	
	public double getRemainingBalance() {
		return closingBalance;
	}
	
	public List<PaymentAmount> getPayments() {
		return payments;
	}

}
