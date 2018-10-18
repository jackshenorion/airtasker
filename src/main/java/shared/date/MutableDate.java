package shared.date;

import util.shared.collection.IsInt;
import util.shared.primitive.HasValOperations;
import util.shared.primitive.Val;
import util.shared.primitive.Vals;

import java.util.Date;

public final class MutableDate implements HasDate, Comparable<MutableDate>, IsInt, HasValOperations {
	
	static int[] NormalYearDays;
    static int[] LeapYearDays;
    
    static {
		NormalYearDays 	= new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		LeapYearDays	= new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

	private int year;
	private int month;
	private int day;
	private int intValue;
	
	public MutableDate() {
		this(new Date());
	}
	
	public MutableDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		
		normalise();
	}
	
	public MutableDate(Date day) {
		this(day.getYear() + 1900, day.getMonth(), day.getDate());
	}
	
	protected MutableDate(int year, int month, int day, int intValue) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.intValue = intValue;
	}

	public MutableDate clone() {
		return new MutableDate(year, month, day, intValue);
	}

	public boolean before(MutableDate d) {
		return intValue < d.intValue;
	}
	
	public boolean after(MutableDate d) {
		return intValue > d.intValue;
	}
	
	public MutableDate addDays(int n) {
		day += n;
		if (day > 28 || day < 1) {
			normalise();
		}
		else {
			intValue = toInt(this);
		}
		return this;
	}
	
	public MutableDate addMonths(int months) {
		month += months;
		normaliseMonth();
		trimExtraDays();
		normalise();
		return this;
	}
	
	public MutableDate addYears(int years) {
		year += years;
		normalise();
		return this;
	}
	
	public int compareTo(MutableDate o) {
        if (o == null) {
            return 1;
        }
        if (o == this) {
            return 0;
        }
        return intValue - o.intValue;
    }

	public MutableDate setDay(int day) {
		this.day = day;
		if (day > 28 || day < 1) {
			normalise();
		}
		else {
			intValue = toInt(this);
		}
		return this;
	}

	public MutableDate setMonth(int month) {
		this.month = month;
		normalise();
		return this;
	}

	public MutableDate setYear(int year) {
		this.year = year;
		normalise();
		return this;
	}

	@Override
	public Val add(Val val) {
		if (val.isNumber()) {
			return Vals.of(clone().addDays(val.asInt()));
		}
		if (val.isObject()) {
			Object obj = val.toObject();
			if (obj.getClass() == MutableDate.class) {
				return Vals.of(toExcelDate() + ((MutableDate) obj).toExcelDate());
			}
		}
		return Vals.of(this);
	}

	@Override
	public Val isAddedBy(Val val) {
		return add(val);
	}

	@Override
	public Val divide(Val val) {
		if (val.isNumber()) {
			return Vals.of(fromExcelDate((int) (toExcelDate() / val.asDouble())));
		}
		if (val.isObject()) {
			Object obj = val.toObject();
			if (obj.getClass() == MutableDate.class) {
				return Vals.of(toExcelDate() * ((MutableDate) obj).toExcelDate());
			}
		}
		return Vals.of(this);
	}

	@Override
	public Val isDividedBy(Val val) {
		if (val.isNumber()) {
			return Vals.of(fromExcelDate((int)(val.asDouble() / toExcelDate())));
		}
		if (val.isObject()) {
			Object obj = val.toObject();
			if (obj.getClass() == MutableDate.class) {
				return Vals.of(((MutableDate) obj).toExcelDate() / toExcelDate());
			}
		}
		return Vals.of(this);
	}

	@Override
	public Val multiply(Val val) {
		if (val.isNumber()) {
			return Vals.of(fromExcelDate((int)(toExcelDate() * val.asDouble())));
		}
		if (val.isObject()) {
			Object obj = val.toObject();
			if (obj.getClass() == MutableDate.class) {
				return Vals.of(toExcelDate() * ((MutableDate) obj).toExcelDate());
			}
		}
		return Vals.of(this);
	}

	@Override
	public Val isMultipliedBy(Val val) {
		return multiply(val);
	}

	@Override
	public Val subtract(Val val) {
		if (val.isNumber()) {
			return Vals.of(clone().addDays(-val.asInt()));
		}
		if (val.isObject()) {
			Object obj = val.toObject();
			if (obj.getClass() == MutableDate.class) {
				return Vals.of(toExcelDate() - ((MutableDate) obj).toExcelDate());
			}
		}
		return Vals.of(this);
	}

	@Override
	public Val isSubtractedBy(Val val) {
		if (val.isNumber()) {
			return Vals.of(fromExcelDate(val.asInt() - toExcelDate()));
		}
		if (val.isObject()) {
			Object obj = val.toObject();
			if (obj.getClass() == MutableDate.class) {
				return Vals.of(((MutableDate) obj).toExcelDate() - toExcelDate());
			}
		}
		return val;
	}

	@Override
    public MutableDate resolve(MutableDate base) {
        return this;
    }

	@Override
	public int hashCode() {
		return intValue;
	}

	public boolean equals(MutableDate d) {
		return intValue == d.intValue;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o != null && o instanceof MutableDate) {
			MutableDate d = (MutableDate) o;
			return intValue == d.intValue;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.year + "-" + (this.month + 1) + "-" + this.day;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int toExcelDate() {
		return toExcelDate(this);
	}

	public Date toDate() {
		return new Date(year - 1900, month, day);
	}

	public Date toDate(int hour, int minute, int second) {
		return new Date(year - 1900, month, day, hour, minute, second);
	}

	@Override
	public int toInt() {
		return intValue;
	}

	private void trimExtraDays() {
		boolean leap = DateUtil.isLeapYear(year);
		int daysOfMonth = leap ? LeapYearDays[month] : NormalYearDays[month];
		day = Math.min(day, daysOfMonth);
	}
	
	/**
	 * This method may contains some boilerplate code that is to help it run as
	 * fast as it can.
	 */
	private void normalise() {

		// normalise month
		if (month > 11 || month < 0) {
			normaliseMonth();
		}
		
		// normalise date
		int y = year;
		int m = month;
		int d = day;
		boolean leap = DateUtil.isLeapYear(y);
		
		if (d > 28)  {
			int daysOfMonth = leap ? LeapYearDays[m] : NormalYearDays[m];
			while (d > daysOfMonth) {
				m = ++month;
				if (m > 11 || m < 0) {
					m = normaliseMonth(); // normaliseMonth may change month
				}
				d -= daysOfMonth;
				
				if (y != year) {
					y = year;
					leap = DateUtil.isLeapYear(y);
				}
				daysOfMonth = leap ? LeapYearDays[m] : NormalYearDays[m];
			}
		}
		
		while (d <= 0) {
			m = --month;
			if (m > 11 || m < 0) {
				m = normaliseMonth();
			}
			if (y != year) {
				y = year;
				leap = DateUtil.isLeapYear(y);
			}
			d += leap ? LeapYearDays[m] : NormalYearDays[m];
		}
		
		day = d;
		intValue = toInt(this);
	}
	
	private int normaliseMonth() {
		int m = month;
		if (m > 11) {
			int delta = m % 12;
			year += m / 12;
			m = month = delta;
		}
		else if (m < 0) {
			int delta = m % 12;
			year += m / 12;
			if (delta != 0) {
				year--;
				m = month = 12 + delta;
			}
			else {
				m = month = 0;
			}
		}
		return m;
	}

	public static MutableDate fromInt(int n) {
		return new MutableDate(n / 10000, (n % 10000) / 100, n % 100);
	}

	public static int toInt(MutableDate date) {
		return date.getYear() * 10000 + date.getMonth() * 100 + date.getDay();
	}

	public static MutableDate fromExcelDate(int days) {
		if (days == 0) {
			return new MutableDate(1899, 11, 30);
		}
		return new MutableDate(1899, 11, 31).addDays(days - 1);
	}

	public static int toExcelDate(MutableDate date) {
		int year = date.year;
		int month = date.month;
		int day = date.day;
		int total = 0;
		if (year >= 1900) {
			for (int y = 1900; y < year; y++) {
				total += DateUtil.isLeapYear(y) ? 366 : 365;
			}
			boolean isLeapYear = DateUtil.isLeapYear(year);
			for (int m = 0; m < month; m++) {
				total += isLeapYear ? LeapYearDays[m] : NormalYearDays[m];
			}
			return total + day + 1;
		}
		else {
			for (int y = 1900 - 1; y > year; y--) {
				total += DateUtil.isLeapYear(y) ? 366 : 365;
			}
			boolean isLeapYear = DateUtil.isLeapYear(year);
			for (int m = 11; m > month; m--) {
				total += isLeapYear ? LeapYearDays[m] : NormalYearDays[m];
			}
			return - (total + LeapYearDays[month] - day - 1);
		}
	}

}
