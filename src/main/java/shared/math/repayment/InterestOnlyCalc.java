package shared.math.repayment;

import java.util.ArrayList;
import java.util.List;

public final class InterestOnlyCalc {
	
	private double totalLoanAmount;
	private double periodicRate;
	private double repaymentAmount;
	private int totalTerm;
	private int scheduledTerm;
	private boolean includePaymentList = true;

	public InterestOnlyCalc(double totalLoanAmount, double periodicRate, int totalTerm) {
		this(totalLoanAmount, periodicRate, totalTerm, totalTerm);
	}

	/**
	 * @param scheduledTerm - indicates that calculation should cover specified amount of time.
	 *                        It is useful for calculating the first N years of an interest only loan.
	 */
	public InterestOnlyCalc(double totalLoanAmount, double periodicRate, int totalTerm, int scheduledTerm) {
		this.totalLoanAmount = totalLoanAmount;
		this.periodicRate = periodicRate;
		this.totalTerm = totalTerm;
		this.repaymentAmount = totalLoanAmount * periodicRate;
		this.scheduledTerm = scheduledTerm;
	}

	/**
	 * Turn off payment list can reduce memory footprint, in which case the payment list in generated summary will be empty.
	 */
	public InterestOnlyCalc setIncludePaymentList(boolean includePaymentList) {
		this.includePaymentList = includePaymentList;
		return this;
	}

	/**
	 * Calculate repayment summary. This method has much smaller memory footprints comparing to {@link #calcRepaymentSchedule()},
	 * it is a perfect suit if you don't need evert payment detail.
	 */
	public RepaymentSummary calcPaymentSummary() {
		List<PaymentAmount> payments = new ArrayList<>();
		double totalInterest = 0;

		for (int month = 1; month <= scheduledTerm; month++) {
			totalInterest += repaymentAmount;

			if (includePaymentList) {
				PaymentAmount payment = new PaymentAmount();
				payment.principal = 0;
				payment.interest = repaymentAmount;
				payments.add(payment);
			}
		}
		return new RepaymentSummary(false, totalLoanAmount, repaymentAmount, totalInterest, periodicRate, scheduledTerm, totalLoanAmount, payments);
	}

	/**
	 * Calculate repayment schedule. This method has larger memory footprints comparing to {@link #calcPaymentSummary()},
	 * use it with caution when running on server environment.
	 */
	public RepaymentSchedule calcRepaymentSchedule() {
		List<Payment> payments = new ArrayList<>();
		double totalInterest = 0;

		for (int month = 1; month <= scheduledTerm; month++) {
			totalInterest += repaymentAmount;

			if (includePaymentList) {
				Payment payment = new Payment();
				payment.index = month;
				payment.principal = 0;
				payment.interest = repaymentAmount;
				payment.repaymentAmount = repaymentAmount;
				payment.extraRepayment = 0;

				payment.openingBalance = totalLoanAmount;
				payment.closingBalance = totalLoanAmount;
				payment.cumulativeInterest = totalInterest;
				payment.cumulativeRepayment = totalInterest;
				payment.cumulativeOffset = 0;
				payments.add(payment);
			}
		}
		return new RepaymentSchedule(false, totalLoanAmount, repaymentAmount, totalInterest, periodicRate, scheduledTerm, payments);
	}
}