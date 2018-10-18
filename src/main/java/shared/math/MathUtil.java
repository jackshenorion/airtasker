package shared.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    /**
     * inverse hyperbolic cosine
     * @param d
     */
    public static double acosh(double d) {
        return Math.log(Math.sqrt(Math.pow(d, 2) - 1) + d);
    }

    /**
     * Calculate number of payment based on a constant repayment amount.
     *
     * @param loanAmount
     *            - amount of principal, net of initial payments, meaning
     *            "subtract any down-payments"
     * @param interestRate
     *            - periodic interest rate
     * @param amountOfPayment
     *            - amount for each payment
     * @return periodic payment amount
     */
    public static double nper(double loanAmount, double interestRate, double amountOfPayment) {
        double t = Math.log(amountOfPayment) - Math.log(amountOfPayment - interestRate * loanAmount );
        double b = Math.log(1 + interestRate);
        return t / b;
    }

    /**
     * inverse hyperbolic sine
     * @param d
     */
    public static double asinh(double d) {
        return Math.log(Math.sqrt(d*d + 1) + d);
    }

    /**
     * inverse hyperbolic tangent
     * @param d
     */
    public static double atanh(double d) {
        return Math.log((1 + d)/(1 - d)) / 2;
    }

    /**
     * average of all values
     * @param values
     */
    public static double average(double[] values) {
        double ave = 0;
        double sum = 0;
        for (int i=0, iSize=values.length; i<iSize; i++) {
            sum += values[i];
        }
        ave = sum / values.length;
        return ave;
    }

    /**
     * Note: this function is different from java.lang.Math.ceil(..).
     * <p>
     * When n and s are "valid" arguments, the returned value is: Math.ceiling(n/s) * s;
     * <br/>
     * n and s are invalid if any of following conditions are true:
     * <ul>
     * <li>s is zero</li>
     * <li>n is negative and s is positive</li>
     * <li>n is positive and s is negative</li>
     * </ul>
     * In all such cases, Double.NaN is returned.
     * @param n
     * @param s
     */
    public static double ceiling(double n, double s) {
        double c;

        if ((n<0 && s>0) || (n>0 && s<0)) {
            c = Double.NaN;
        }
        else {
            c = (n == 0 || s == 0) ? 0 : Math.ceil(n/s) * s;
        }

        return c;
    }

    /**
     * Use the given <tt>n</tt> as base and returns a value that falls in the given range.
     */
    public static Comparable clamp(Comparable n, Comparable minInclusive, Comparable maxInclusive) {
        return 	(n.compareTo(minInclusive) < 0) ? minInclusive :
            (n.compareTo(maxInclusive) > 0) ? maxInclusive : n;
    }

    /**
     * Use the given <tt>n</tt> as base and returns a value that falls in the given range.
     */
    public static double clamp(double n, double minInclusive, double maxInclusive) {
        return 	(n < minInclusive) ? minInclusive :
            (n > maxInclusive) ? maxInclusive : n;
    }

    /**
     * Use the given <tt>n</tt> as base and returns a value that falls in the given range.
     */
    public static float clamp(float n, float minInclusive, float maxInclusive) {
        return 	(n < minInclusive) ? minInclusive :
            (n > maxInclusive) ? maxInclusive : n;
    }

    /**
     * Use the given <tt>n</tt> as base and returns a value that falls in the given range.
     */
    public static int clamp(int n, int minInclusive, int maxInclusive) {
        return 	(n < minInclusive) ? minInclusive :
            (n > maxInclusive) ? maxInclusive : n;
    }

    /**
     * Use the given <tt>n</tt> as base and returns a value that falls in the given range.
     */
    public static long clamp(long n, long minInclusive, long maxInclusive) {
        return 	(n < minInclusive) ? minInclusive :
            (n > maxInclusive) ? maxInclusive : n;
    }

    /**
     * hyperbolic cosine
     * @param d
     */
    public static double cosh(double d) {
        double ePowX = Math.pow(Math.E, d);
        double ePowNegX = Math.pow(Math.E, -d);
        return (ePowX + ePowNegX) / 2;
    }

    /**
     * <br/> for all n >= 1; factorial n = n * (n-1) * (n-2) * ... * 1
     * <br/> else if n == 0; factorial n = 1
     * <br/> else if n < 0; factorial n = Double.NaN
     * <br/> Loss of precision can occur if n is large enough.
     * If n is large so that the resulting value would be greater
     * than Double.MAX_VALUE; Double.POSITIVE_INFINITY is returned.
     * If n < 0, Double.NaN is returned.
     * @param n
     */
    public static double factorial(int n) {
        double d = 1;

        if (n >= 0) {
            if (n <= 170) {
                for (int i=1; i<=n; i++) {
                    d *= i;
                }
            }
            else {
                d = Double.POSITIVE_INFINITY;
            }
        }
        else {
            d = Double.NaN;
        }
        return d;
    }

    /**
     * Note: this function is different from java.lang.Math.floor(..).
     * <p>
     * When n and s are "valid" arguments, the returned value is: Math.floor(n/s) * s;
     * <br/>
     * n and s are invalid if any of following conditions are true:
     * <ul>
     * <li>s is zero</li>
     * <li>n is negative and s is positive</li>
     * <li>n is positive and s is negative</li>
     * </ul>
     * In all such cases, Double.NaN is returned.
     * @param n
     * @param s
     */
    public static double floor(double n, double s) {
        double f;

        if ((n<0 && s>0) || (n>0 && s<0) || (s==0 && n!=0)) {
            f = Double.NaN;
        }
        else {
            f = (n==0 || s==0) ? 0 : Math.floor(n/s) * s;
        }

        return f;
    }

    /**
     * Returns the future value of an investment based on periodic, constant
     * payments and a constant interest rate.
     *
     * @param rate
     *            - is the interest rate per period. rate
     * @param nper
     *            - is the total number of payment periods in an annuity.
     * @param pmt
     *            - is the payment made each period; it cannot change over the
     *            life of the annuity. Typically, pmt contains principal and
     *            interest but no other fees or taxes. If pmt is omitted, you
     *            must include the pv argument.
     * @param pv
     *            future value
     * @param type
     *            false = pmt at end of period, true = pmt at begining of period
     */
    public static double fv(double rate, double nper, double pmt, double pv, boolean type) {
        double retval = 0;
        if (rate == 0) {
            retval = -1 * (pv + (nper * pmt));
        } else {
            double r1 = rate + 1;
            retval = ((1 - Math.pow(r1, nper)) * (type ? r1 : 1) * pmt) / rate
                -
                pv * Math.pow(r1, nper);
        }
        return retval;
    }

    private static double fvFactor(double r, double nper) {
        double x = 1.0 + r;
        double y = nper;
        return Math.pow(x, y);
    }

    /**
     * Returns the interest payment for a given period for an investment based
     * on periodic, constant payments and a constant interest rate.
     *
     * @param rate
     *            - is the interest rate per period.
     * @param per
     *            - is the period for which you want to find the interest and
     *            must be in the range 1 to nper.
     * @param nper
     *            - is the total number of payment periods in an annuity.
     * @param pv
     *            - is the present value, or the lump-sum amount that a series
     *            of future payments is worth right now.
     * @param fv
     *            - is the future value, or a cash balance you want to attain
     *            after the last payment is made. If fv is omitted, it is
     *            assumed to be 0 (the future value of a loan, for example, is
     *            0).
     * @param type
     *            - true indicates that payments are due at the beginning of the
     *            period. false indicates at the end of the period.
     */
    public static double ipmt(double rate, double per, double nper, double pv, double fv, boolean type) {
        double result = -(((pv * fvFactor(rate, per - 1.0)) * rate) + (pmt(rate, nper, pv, fv, true) * (fvFactor(rate, per - 1.0) - 1.0)));
        if (!type) {
            return result;
        }
        return (result / (1.0 + rate));
    }



    /**
     * Returns {@code true} if the argument is a finite floating-point
     * value; returns {@code false} otherwise (for NaN and infinity
     * arguments).
     *
     * The implementation is a copy of Double.isFinite() in Java 8 so that it is available for Android.
     */
    private static final double MAX_VALUE = 1.7976931348623157E308D;
    public static boolean isFinite(double d) {
        return Math.abs(d) <= MAX_VALUE;
    }

    /**
     * min of all values. If supplied array is zero length,
     * Double.NEGATIVE_INFINITY is returned.
     * @param values
     */
    public static double max(double[] values) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i=0, iSize=values.length; i<iSize; i++) {
            max = Math.max(max, values[i]);
        }
        return max;
    }

    /**
     * min of all values. If supplied array is zero length,
     * Double.POSITIVE_INFINITY is returned.
     * @param values
     */
    public static double min(double[] values) {
        double min = Double.POSITIVE_INFINITY;
        for (int i=0, iSize=values.length; i<iSize; i++) {
            min = Math.min(min, values[i]);
        }
        return min;
    }

    /**
     * returns the remainder resulting from operation:
     * n / d.
     * <br/> The result has the sign of the divisor.
     * <br/> Examples:
     * <ul>
     * <li>mod(3.4, 2) = 1.4</li>
     * <li>mod(-3.4, 2) = 0.6</li>
     * <li>mod(-3.4, -2) = -1.4</li>
     * <li>mod(3.4, -2) = -0.6</li>
     * </ul>
     * If d == 0, result is NaN
     * @param n
     * @param d
     */
    public static double mod(double n, double d) {
        double result = 0;

        if (d == 0) {
            result = Double.NaN;
        }
        else if (sign(n) == sign(d)) {
            double t = Math.abs(n / d);
            t = t - (long) t;
            result = sign(d) * Math.abs(t * d);
        }
        else {
            double t = Math.abs(n / d);
            t = t - (long) t;
            t = Math.ceil(t) - t;
            result = sign(d) * Math.abs(t * d);
        }

        return result;
    }

    /**
     * returns the total number of combinations possible when
     * k items are chosen out of total of n items. If the number
     * is too large, loss of precision may occur (since returned
     * value is double). If the returned value is larger than
     * Double.MAX_VALUE, Double.POSITIVE_INFINITY is returned.
     * If either of the parameters is negative, Double.NaN is returned.
     * @param n
     * @param k
     */
    public static double nChooseK(int n, int k) {
        double d = 1;
        if (n<0 || k<0 || n<k) {
            d= Double.NaN;
        }
        else {
            int minnk = Math.min(n-k, k);
            int maxnk = Math.max(n-k, k);
            for (int i=maxnk; i<n; i++) {
                d *= i+1;
            }
            d /= factorial(minnk);
        }

        return d;
    }

    /**
     * Returns the number of periods for an investment based on periodic,
     * constant payments and a constant interest rate.
     *
     * For a more complete description of the arguments in NPER and for more
     * information about annuity functions, see PV.
     *
     * @param rate
     *            - is the interest rate per period.
     * @param pmt
     *            - is the payment made each period; it cannot change over the
     *            life of the annuity. Typically, pmt contains principal and
     *            interest but no other fees or taxes.
     * @param pv
     *            - is the present value, or the lump-sum amount that a series
     *            of future payments is worth right now.
     * @param fv
     *            - is the future value, or a cash balance you want to attain
     *            after the last payment is made. If fv is omitted, it is
     *            assumed to be 0 (the future value of a loan, for example, is
     *            0).
     * @param type
     *            - false = pmt at end of period, true = pmt at begining of
     *            period
     */
    public static double nper(double rate, double pmt, double pv, double fv, boolean type) {
        double retval = 0;
        if (rate == 0) {
            retval = -1 * (fv + pv) / pmt;
        } else {
            double r1 = rate + 1;
            double ryr = (type ? r1 : 1) * pmt / rate;
            double a1 = ((ryr - fv) < 0)
                    ? Math.log(fv - ryr)
                    : Math.log(ryr - fv);
            double a2 = ((ryr - fv) < 0)
                    ? Math.log(-pv - ryr)
                    : Math.log(pv + ryr);
            double a3 = Math.log(r1);
            retval = (a1 - a2) / a3;
        }
        return retval;
    }

    /**
     * Calculates the net present value of an investment by using a discount
     * rate and a series of future payments (negative values) and income
     * (positive values).
     *
     * @param rate
     *            - is the rate of discount over the length of one period.
     * @param values
     *            - are 1 to 254 arguments representing the payments and income.
     *            <ul>
     *            <li>Value1, value2, ... must be equally spaced in time and
     *            occur at the end of each period.</li>
     *            <li>NPV uses the order of value1, value2, ... to interpret the
     *            order of cash flows. Be sure to enter your payment and income
     *            values in the correct sequence.</li>
     *            <li>Arguments that are numbers, empty cells, logical values,
     *            or text representations of numbers are counted; arguments that
     *            are error values or text that cannot be translated into
     *            numbers are ignored.</li>
     *            <li>If an argument is an array or reference, only numbers in
     *            that array or reference are counted. Empty cells, logical
     *            values, text, or error values in the array or reference are
     *            ignored.</li>
     *            </ul>
     */
    public static double npv(double rate, double[] values) {
        double npv = 0;
        double r1 = rate + 1;
        double trate = r1;
        for (int i=0, iSize=values.length; i<iSize; i++) {
            npv += values[i] / trate;
            trate *= r1;
        }
        return npv;
    }

    /**
     * Calculates the payment for a loan based on constant payments and a
     * constant interest rate.
     *
     * @param rate
     *            - is the interest rate for the loan.
     * @param nper
     *            - is the total number of payments for the loan.
     * @param pv
     *            - is the present value, or the total amount that a series of
     *            future payments is worth now; also known as the principal.
     * @param fv
     *            - is the future value, or a cash balance you want to attain
     *            after the last payment is made. If fv is omitted, it is
     *            assumed to be 0 (zero), that is, the future value of a loan is
     *            0.
     * @param type
     *            - false = pmt at end of period, true = pmt at begining of
     *            period
     */
    public static double pmt(double rate, double nper, double pv, double fv, boolean type) {
        double retval = 0;
        if (rate == 0) {
            retval = -1*(fv+pv)/nper;
        }
        else {
            double r1 = rate + 1;
            retval = ( fv + pv * Math.pow(r1, nper) ) * rate
                      /
                   ((type ? r1 : 1) * (1 - Math.pow(r1, nper)));
        }
        return retval;
    }

    /**
     * Returns the present value of an investment. The present value is the
     * total amount that a series of future payments is worth now. For example,
     * when you borrow money, the loan amount is the present value to the
     * lender.
     *
     * @param rate
     *            - is the interest rate per period. For example, if you obtain
     *            an automobile loan at a 10 percent annual interest rate and
     *            make monthly payments, your interest rate per month is 10%/12,
     *            or 0.83%. You would enter 10%/12, or 0.83%, or 0.0083, into
     *            the formula as the rate.
     * @param nper
     *            - is the total number of payment periods in an annuity. For
     *            example, if you get a four-year car loan and make monthly
     *            payments, your loan has 4*12 (or 48) periods. You would enter
     *            48 into the formula for nper.
     * @param pmt
     *            - is the payment made each period and cannot change over the
     *            life of the annuity. Typically, pmt includes principal and
     *            interest but no other fees or taxes. For example, the monthly
     *            payments on a $10,000, four-year car loan at 12 percent are
     *            $263.33. You would enter -263.33 into the formula as the pmt.
     *            If pmt is omitted, you must include the fv argument.
     * @param fv
     *            - is the future value, or a cash balance you want to attain
     *            after the last payment is made. If fv is omitted, it is
     *            assumed to be 0 (the future value of a loan, for example, is
     *            0). For example, if you want to save $50,000 to pay for a
     *            special project in 18 years, then $50,000 is the future value.
     *            You could then make a conservative guess at an interest rate
     *            and determine how much you must save each month. If fv is
     *            omitted, you must include the pmt argument.
     * @param type
     *            false = pmt at end of period, true = pmt at begining of period
     */
    public static double pv(double rate, double nper, double pmt, double fv, boolean type) {
        double retval = 0;
        if (rate == 0) {
            retval = -1*((nper*pmt)+fv);
        }
        else {
            double r1 = rate + 1;
            retval =(( ( 1 - Math.pow(r1, nper) ) / rate ) * (type ? r1 : 1)  * pmt - fv)
                     /
                    Math.pow(r1, nper);
        }
        return retval;
    }

    /**
     * Return a random integer in a range.
     */
    public static int randomInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static double rate(double nper, double pmt, double pv, double fv, int type) {
        return rate(nper, pmt, pv, fv, type, 0.1);
    }

    public static double rate(double nper, double pmt, double pv, double fv, int type, double guess) {

        int FINANCIAL_MAX_ITERATIONS = 20;
        double FINANCIAL_PRECISION = 1.0e-08;

        double rate = guess;
        double y = 0;
        double f = 0;

        if (Math.abs(rate) < FINANCIAL_PRECISION) {
            y = pv * (1 + nper * rate) + pmt * (1 + rate * type) * nper + fv;
        }
        else {
            f = Math.exp(nper * Math.log(1 + rate));
            y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
        }

        double y0 = pv + pmt * nper + fv;
        double y1 = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;

        // find root by secant method
        double i  = 0;
        double x0 = 0.0;
        double x1 = rate;

        while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION) && (i < FINANCIAL_MAX_ITERATIONS)) {
            rate = (y1 * x0 - y0 * x1) / (y1 - y0);
            x0 = x1;
            x1 = rate;

            if (Math.abs(rate) < FINANCIAL_PRECISION) {
                y = pv * (1 + nper * rate) + pmt * (1 + rate * type) * nper + fv;
            }
            else {
                f = Math.exp(nper * Math.log(1 + rate));
                y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
            }

            y0 = y1;
            y1 = y;
            ++i;
        }
        return rate;
    }

    /**
     * Returns a value rounded to p digits after decimal.
     * If p is negative, then the number is rounded to
     * places to the left of the decimal point. eg.
     * 10.23 rounded to -1 will give: 10. If p is zero,
     * the returned value is rounded to the nearest integral
     * value.
     * <p>If n is negative, the resulting value is obtained
     * as the round value of absolute value of n multiplied
     * by the sign value of n (@see MathFunctions.sign(double d)).
     * Thus, -0.6666666 rounded to p=0 will give -1 not 0.
     * <p>If n is NaN, returned value is NaN.
     * @param n
     * @param p
     */
    public static double round(double n, int p) {
        double retval;

        if (Double.isNaN(n) || Double.isInfinite(n)) {
            retval = Double.NaN;
        }
        else {
            retval = BigDecimal.valueOf(n).setScale(p, RoundingMode.HALF_UP).doubleValue();
        }

        return retval;
    }

    /**
     * Returns a value rounded to p digits after decimal.
     * If p is negative, then the number is rounded to
     * places to the left of the decimal point. eg.
     * 10.23 rounded to -1 will give: 10. If p is zero,
     * the returned value is rounded to the nearest integral
     * value.
     * <p>If n is negative, the resulting value is obtained
     * as the round-up value of absolute value of n multiplied
     * by the sign value of n (@see MathFunctions.sign(double d)).
     * Thus, -0.8 rounded-down to p=0 will give 0 not -1.
     * <p>If n is NaN, returned value is NaN.
     * @param n
     * @param p
     */
    public static double roundDown(double n, int p) {
        double retval;

        if (Double.isNaN(n) || Double.isInfinite(n)) {
            retval = Double.NaN;
        }
        else {
            retval = BigDecimal.valueOf(n).setScale(p, RoundingMode.DOWN).doubleValue();
        }

        return retval;
    }

    /**
     * Returns a value rounded-up to p digits after decimal.
     * If p is negative, then the number is rounded to
     * places to the left of the decimal point. eg.
     * 10.23 rounded to -1 will give: 20. If p is zero,
     * the returned value is rounded to the nearest integral
     * value.
     * <p>If n is negative, the resulting value is obtained
     * as the round-up value of absolute value of n multiplied
     * by the sign value of n (@see MathFunctions.sign(double d)).
     * Thus, -0.2 rounded-up to p=0 will give -1 not 0.
     * <p>If n is NaN, returned value is NaN.
     * @param n
     * @param p
     */
    public static double roundUp(double n, int p) {
        double retval;

        if (Double.isNaN(n) || Double.isInfinite(n)) {
            retval = Double.NaN;
        }
        else {
            retval = BigDecimal.valueOf(n).setScale(p, RoundingMode.UP).doubleValue();
        }

        return retval;
    }

    /**
     * If d < 0, returns short -1
     * <br/>
     * If d > 0, returns short 1
     * <br/>
     * If d == 0, returns short 0
     * <p> If d is NaN, then 1 will be returned. It is the responsibility
     * of caller to check for d isNaN if some other value is desired.
     * @param d
     */
    public static short sign(double d) {
        return (short) ((d == 0)
                ? 0
                : (d < 0)
                        ? -1
                        : 1);
    }

    /**
     * hyperbolic sine
     * @param d
     */
    public static double sinh(double d) {
        double ePowX = Math.pow(Math.E, d);
        double ePowNegX = Math.pow(Math.E, -d);
        return (ePowX - ePowNegX) / 2;
    }

    /**
     * sum of all values
     * @param values
     */
    public static double sum(double[] values) {
        double sum = 0;
        for (int i=0, iSize=values.length; i<iSize; i++) {
            sum += values[i];
        }
        return sum;
    }

    /**
     * sum of squares of all values
     * @param values
     */
    public static double sumsq(double[] values) {
        double sumsq = 0;
        for (int i=0, iSize=values.length; i<iSize; i++) {
            sumsq += values[i]*values[i];
        }
        return sumsq;
    }

    /**
     * hyperbolic tangent
     * @param d
     */
    public static double tanh(double d) {
        double ePowX = Math.pow(Math.E, d);
        double ePowNegX = Math.pow(Math.E, -d);
        return (ePowX - ePowNegX) / (ePowX + ePowNegX);
    }

    /**
     * simulate the MS Excel Trunc Function (http://www.techonthenet.com/excel/formulas/trunc.php)
     * @param n
     * @param d
     */
    public static double trunc(double n, int d) {
        double dd = Math.pow(10, d);
        return n > 0 ? Math.floor(n * dd) / dd : Math.ceil(n * dd) / dd;
    }

    /**
     * Convert a decimal number to  String.
     */
    public static String toString(double n, int precision) {
        return BigDecimal.valueOf(n).setScale(precision, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }
}
