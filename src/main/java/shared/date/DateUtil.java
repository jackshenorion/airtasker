package shared.date;

import java.util.Date;

public class DateUtil {
	
    public static final Date Epoch = new Date(0);
	public static final ShortDate EpochS = new ShortDate(0);
	public static final Date Year2050 = new Date(150, 0, 1);
	public static final ShortDate Year2050S = new ShortDate(Year2050);

    private static final long MS_PER_SEC = 1000;
    private static final long MS_PER_MIN = MS_PER_SEC * 60;
    private static final long MS_PER_HOUR = MS_PER_MIN * 60;
    private static final long MS_PER_DAY = MS_PER_HOUR * 24;
	private static final long ONE_MINUTE_IN_MILLIS = 60000;
	private static final long ONE_HOUR_IN_MILLIS = 60000 * 60;

    static int[] NormalYearDays;
    static int[] LeapYearDays;
    
    static {
		NormalYearDays 	= new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		LeapYearDays	= new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

	public static Date addMinutes(Date date, int minutes) {
		return new Date(date.getTime() + minutes * ONE_MINUTE_IN_MILLIS);
	}

	public static Date addHours(Date date, int hours) {
		return new Date(date.getTime() + hours * ONE_HOUR_IN_MILLIS);
	}
    
	public static Date addDays(Date date, int days) {
		Date newDate = new Date(date.getTime());
		newDate.setDate(date.getDate() + days);
		return newDate;
	}

	public static Date addMonths(Date date, int months) {
		for (; months < 0; months++) {
			Date roundedPriorMonth = addDays(truncateToMonth(date), -1);
			date = addDays(date, -getDaysOfMonth(roundedPriorMonth));
		}
		for (; months > 0; months--) {
			date = addDays(date, getDaysOfMonth(date));
		}
		return date;
	}

	public static Date clone(Date date) {
		return (Date) date.clone();
	}

	public static int dayDiff(MutablePeriod period) {
		return dayDiff(period.getStart(), period.getEnd());
	}

	/**
	 * Determines the number of days between two dates.
	 */
	public static int dayDiff(MutableDate startDate, MutableDate endDate) {
		
		if (startDate.equals(endDate)) {
			return 0;
		}
		
		int y1 = startDate.getYear();
		int m1 = startDate.getMonth();
		int d1 = startDate.getDay();
	
		int y2 = endDate.getYear();
		int m2 = endDate.getMonth();
		int d2 = endDate.getDay();
		
		int days = 0;
		
		boolean afterFeb28 = m1 > 1 || (m1 == 1 && d1 > 28); 
		
		if (startDate.before(endDate)) {
			int monthsDiff = 0;
			int yearsDiff = 0;
			
			if (m1 <= m2) {
				yearsDiff = y2 - y1;
				monthsDiff = m2 - m1;
			}
			else {
				yearsDiff = y2 - y1 - 1;
				monthsDiff = m2 + 12 - m1;
			}
			
			while (yearsDiff > 0) {
				days += isLeapYear(afterFeb28 ? y1 + 1 : y1) ? 366 : 365;
				y1++;
				yearsDiff--;
			}
			
			boolean y1Leap = isLeapYear(y1);		
			while (monthsDiff > 0) {
				int remaining = (y1Leap ? LeapYearDays[m1] : NormalYearDays[m1]) - d1;
				days += remaining;
				if (m1 == 11) {
					m1 = 0;
					y1++;
					y1Leap = isLeapYear(y1);
				}
				else {
					m1++;
				}
				d1 = 0;
				monthsDiff--;
			}
			days += d2 - d1;
			return days;
		}
		
		// startDate is after endDate
		int monthsDiff = (y1 * 12 + m1) - (y2 * 12 + m2);
		boolean y1Leap = isLeapYear(y1);	
		
		while (monthsDiff > 0) {
			days -= d1;
			if (m1 == 0) {
				m1 = 11;
				y1--;
				y1Leap = isLeapYear(y1);
			}
			else {
				m1--;
			}
			d1 = y1Leap ? LeapYearDays[m1] : NormalYearDays[m1];
			monthsDiff--;
		}
		days -= d1 - d2;
		return days;
	}
	
	/**
	 * Determines the number of days between two dates, always rounding up so a
	 * difference of 1 day 1 second yield a return value of 2.
	 */
	public static int dayDiffApprox(Date startDate, Date endDate) {
        double d = (endDate.getTime() - startDate.getTime()) / (double)MS_PER_DAY;
		return (int)(d >= 0 ? Math.ceil(d) : Math.floor(d));
    }	

	public static int getDaysOfMonth(Date date) {
		return getDaysOfMonth(date.getYear() + 1900, date.getMonth());
	}
   
	public static int getDaysOfMonth(MutableMonth month) {
		return getDaysOfMonth(month.getYear(), month.getMonth());
	}

	public static MutableDate getFirstDayOfTheMonth(MutableDate date) {
		return new MutableDate(date.getYear(), date.getMonth(), 1);
	}

	public static MutableDate getLastDayOfTheMonth(MutableDate date) {
		return new MutableDate(date.getYear(), date.getMonth(), getDaysOfMonth(date.getYear(), date.getMonth()));
	}

    public static int getDaysOfMonth(int year, int month) {
		if (month == 1) {
			if (isLeapYear(year)) {
				return LeapYearDays[month];
			}
		}
		return NormalYearDays[month];
    }

    public static boolean isLeapYear(int year) {
		return ((year % 100) != 0) && (year % 4) == 0 || (year % 400) == 0;
	}

	public static Date setTime(Date date, int hour, int minute, int second) {
		return new Date(date.getYear(), date.getMonth(), date.getDate(), hour, minute, second);
	}

	public static Date truncateToDay(Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDate());
	}

	public static Date truncateToMonth(Date date) {
		return addDays(truncateToDay(date), 1 - date.getDate());
	}

	public static Date truncateTime(Date date) {
		return setTime(date, 0, 0, 0);
	}

}
