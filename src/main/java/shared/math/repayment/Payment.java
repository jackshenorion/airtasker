package shared.math.repayment;

import util.shared.math.MathUtil;

public class Payment {

	int index;
	double principal;
	double interest;
	double repaymentAmount;
	double extraRepayment;
	double openingBalance;
	double closingBalance;
	double cumulativeInterest;
	double cumulativeRepayment;
	double cumulativeOffset;

	public Payment() {
	}

	public Payment(Payment another) {
		this.index = another.index;
		this.principal = another.principal;
		this.interest = another.interest;
		this.repaymentAmount = another.repaymentAmount;
		this.extraRepayment = another.extraRepayment;
		this.openingBalance = another.openingBalance;
		this.closingBalance = another.closingBalance;
		this.cumulativeInterest = another.cumulativeInterest;
		this.cumulativeRepayment = another.cumulativeRepayment;
		this.cumulativeOffset = another.cumulativeOffset;
	}

	public int getIndex() {
		return index;
	}

	public Payment setIndex(int index) {
		this.index = index;
		return this;
	}

	public double getPrincipal() {
		return principal;
	}

	public double getInterest() {
		return interest;
	}

	public double getRepaymentAmount() {
		return repaymentAmount;
	}

	public double getExtraRepayment() {
		return extraRepayment;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public double getClosingBalance() {
		return closingBalance;
	}

	public double getCumulativeInterest() {
		return cumulativeInterest;
	}

	public double getCumulativeRepayment() {
		return cumulativeRepayment;
	}

	public double getCumulativeOffset() {
		return cumulativeOffset;
	}

	@Override
	public String toString() {
		return "Payment{" +
			"index=" + index +
			", principal=" + MathUtil.round(principal, 2) +
			", interest=" + MathUtil.round(interest, 2) +
			", repaymentAmount=" + MathUtil.round(repaymentAmount, 2) +
			", extraPayment=" + MathUtil.round(extraRepayment, 2) +
			", openingBalance=" + MathUtil.round(openingBalance, 2) +
			", closingBalance=" + MathUtil.round(closingBalance, 2) +
			", cumulativeInterest=" + MathUtil.round(cumulativeInterest, 2) +
			", cumulativeRepayment=" + MathUtil.round(cumulativeRepayment, 2) +
			", cumulativeOffset=" + MathUtil.round(cumulativeOffset, 2) +
			'}';
	}
}
