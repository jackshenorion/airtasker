package shared.math.repayment;

import util.shared.math.MathUtil;

import java.util.List;

public class RepaymentSchedule {
	
	private boolean principalAndInterest;
	private double initialLoanAmount;
	private double periodicPayment;
	private double totalInterest;
	private double periodicRate;
	private int totalTerm;
	
	private List<Payment> payments;

	public RepaymentSchedule(boolean principalAndInterest,
							 double totalLoanAmount,
							 double periodicPayment,
							 double totalInterest,
							 double periodicRate,
							 int totalTerm,
							 List<Payment> payments) {
		
		this.principalAndInterest = principalAndInterest;
		this.initialLoanAmount = totalLoanAmount;
		this.periodicPayment = periodicPayment;
		this.totalInterest = totalInterest;
		this.periodicRate = periodicRate;
		this.totalTerm = totalTerm;
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
		if (payments.isEmpty()) { // should not happen. just in case
			return initialLoanAmount;
		}
		return payments.get(payments.size() - 1).getClosingBalance();
	}
	
	public List<Payment> getPayments() {
		return payments;
	}

	public String generateCSV() {
		StringBuilder csv = new StringBuilder(payments.size() * 100);
		csv.append(
			"index, opening balance, interest, cumulative interest, principal, repayment, extra payment, " +
				"cumulative repayment, cumulative offset, closing balance\n"
		);

		for (int i = 0, total = payments.size(); i < total; i++) {
			Payment payment = payments.get(i);
			csv.append(i + 1).append(',')
				.append(MathUtil.round(payment.openingBalance, 2)).append(',')
				.append(MathUtil.round(payment.interest, 2)).append(',')
				.append(MathUtil.round(payment.cumulativeInterest, 2)).append(',')
				.append(MathUtil.round(payment.principal, 2)).append(',')
				.append(MathUtil.round(payment.repaymentAmount, 2)).append(',')
				.append(MathUtil.round(payment.extraRepayment, 2)).append(',')
				.append(MathUtil.round(payment.cumulativeRepayment, 2)).append(',')
				.append(MathUtil.round(payment.cumulativeOffset, 2)).append(',')
				.append(MathUtil.round(payment.closingBalance, 2)).append('\n')
			;
		}
		return csv.toString();
	}

}
