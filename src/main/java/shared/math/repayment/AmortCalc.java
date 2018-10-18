package shared.math.repayment;

import util.shared.math.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.vertex42.com/ExcelArticles/amortization-calculation.html
 * 
 *          r (1 + r)^n 
 *   A = P --------------
 *   		(1 + r)^n -1
 * 
 * where
 * 		A = payment Amount per period
 * 		P = initial Principal (loan amount)
 * 		r = interest rate per period
 * 		n = total number of payments or periods
 *   
 */
public class AmortCalc {

	/**
	 * Create an instance of AmortCalc based on total loan terms in years and annual rate.
 	 */
	public static RepaymentSchedule fromYears(int totalLoanAmount, int totalLoanTermYears, double annualRate) {
		return new AmortCalc(
			totalLoanAmount,
			annualRate / 100 / 12,
			totalLoanTermYears * 12
		).calcRepaymentSchedule();
	}

	public static final int MaxLoanTerm = 2080; // 40 yrs * 52 weeks
	private double totalLoanAmount;
	private double periodicRate;
	private double periodicExtraRepayment = 0;
	private double periodicOffsetAmount = 0;
	private int totalTerm;
	private int scheduledTerm;
	private boolean includePaymentList = true;

	public AmortCalc(double totalLoanAmount, double periodicRate, int totalTerm) {
		this(totalLoanAmount, periodicRate, totalTerm, totalTerm);
	}

	/**
	 * @param scheduledTerm - indicates that calculation should cover specified amount of time. It is useful for
	 *                                 calculating the first N years of a fixed rate loan.
	 */
	public AmortCalc(double totalLoanAmount, double periodicRate, int totalTerm, int scheduledTerm) {
		this.totalLoanAmount = totalLoanAmount;
		this.periodicRate = periodicRate;
		this.totalTerm = Math.min(totalTerm, MaxLoanTerm);
		this.scheduledTerm = Math.min(scheduledTerm, MaxLoanTerm);
	}

	public AmortCalc setPeriodicExtraRepayment(double periodicExtraRepayment) {
		this.periodicExtraRepayment = periodicExtraRepayment;
		return this;
	}

	public AmortCalc setPeriodicOffsetAmount(double periodicOffsetAmount) {
		this.periodicOffsetAmount = periodicOffsetAmount;
		return this;
	}

	/**
	 * Turn off payment list can reduce memory footprint, in which case the payment list in generated summary will be empty.
	 */
	public AmortCalc setIncludePaymentList(boolean includePaymentList) {
		this.includePaymentList = includePaymentList;
		return this;
	}

	/**
	 * Calculate repayment summary. This method has much smaller memory footprints comparing to {@link #calcRepaymentSchedule()},
	 * it is a perfect suit if you don't need evert payment detail.
	 */
	public RepaymentSummary calcPaymentSummary() {
		List<PaymentAmount> payments = new ArrayList<>(scheduledTerm);

		double periodicPayment = -MathUtil.pmt(periodicRate, totalTerm, totalLoanAmount, 0, false);
		double openingBalance;
		double closingBalance = totalLoanAmount;
		double interest;
		double actualPayment;
		double actualExtraPayment = periodicExtraRepayment;
		double cumulativeInterest = 0;
		double cumulativeRepayment = 0;
		double cumulativeOffset = 0;
		boolean paidOff = false;

		for (int index = 1; index <= scheduledTerm && !paidOff; index++) {
			openingBalance = closingBalance;

			interest = periodicRate * (Math.max(0, openingBalance - cumulativeOffset));
			cumulativeInterest += interest;

			/**
			 * The actual payment is lower than scheduled when it is the last payment.
			 */
			actualPayment = periodicPayment + periodicExtraRepayment;
			if (openingBalance + interest < actualPayment) {
				actualPayment = openingBalance + interest;
				actualExtraPayment = Math.max(0, actualPayment - periodicPayment);
				paidOff = true;
			}
			cumulativeRepayment += actualPayment;

			cumulativeOffset += periodicOffsetAmount;
			closingBalance = Math.abs(openingBalance - actualPayment + interest); // avoid "-0.0"

			if (includePaymentList) {
				PaymentAmount payment = new PaymentAmount();
				payment.interest = interest;
				payment.principal = actualPayment - interest;
				payments.add(payment);
			}
		}
		return new RepaymentSummary(true, totalLoanAmount, periodicPayment, cumulativeInterest, periodicRate, scheduledTerm, closingBalance, payments);
	}

	/**
	 * Calculate repayment schedule. This method has larger memory footprints comparing to {@link #calcPaymentSummary()},
	 * use it with caution when running on server environment.
	 */
	public RepaymentSchedule calcRepaymentSchedule() {
		List<Payment> payments = new ArrayList<>(scheduledTerm);

		double periodicPayment = -MathUtil.pmt(periodicRate, totalTerm, totalLoanAmount, 0, false);
		double openingBalance;
		double closingBalance = totalLoanAmount;
		double interest;
		double actualPayment;
		double actualExtraPayment = periodicExtraRepayment;
		double cumulativeInterest = 0;
		double cumulativeRepayment = 0;
		double cumulativeOffset = 0;
		boolean paidOff = false;

		for (int index = 1; index <= scheduledTerm && !paidOff; index++) {
			openingBalance = closingBalance;

			interest = periodicRate * (Math.max(0, openingBalance - cumulativeOffset));
			cumulativeInterest += interest;

			/**
			 * The actual payment is lower than scheduled when it is the last payment.
			 */
			actualPayment = periodicPayment + periodicExtraRepayment;
			if (openingBalance + interest < actualPayment) {
				actualPayment = openingBalance + interest;
				actualExtraPayment = Math.max(0, actualPayment - periodicPayment);
				paidOff = true;
			}
			cumulativeRepayment += actualPayment;

			cumulativeOffset += periodicOffsetAmount;
			closingBalance = Math.abs(openingBalance - actualPayment + interest); // avoid "-0.0"

			if (includePaymentList) {
				Payment payment = new Payment();
				payment.index = index;
				payment.interest = interest;
				payment.principal = actualPayment - interest;
				payment.interest = interest;
				payment.repaymentAmount = Math.min(actualPayment, periodicPayment);
				payment.extraRepayment = actualExtraPayment;

				payment.openingBalance = openingBalance;
				payment.closingBalance = closingBalance;
				payment.cumulativeInterest = cumulativeInterest;
				payment.cumulativeRepayment = cumulativeRepayment;
				payment.cumulativeOffset = cumulativeOffset;

				payments.add(payment);
			}
		}
		return new RepaymentSchedule(true, totalLoanAmount, periodicPayment, cumulativeInterest, periodicRate, scheduledTerm, payments);
	}

}