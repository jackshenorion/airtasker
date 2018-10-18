package shared.math.repayment;

public class PaymentAmount {

    double principal;
    double interest;

    public double getPrincipal() {
        return principal;
    }

    public double getInterest() {
        return interest;
    }

    public double getTotal() {
        return principal + interest;
    }
}
