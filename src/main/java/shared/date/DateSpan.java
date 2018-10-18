package shared.date;

import java.util.Date;

public class DateSpan implements HasDate {

	private int years;
	private int months;
	private int days;

	public DateSpan(int years, int months, int days) {
		this.years = years;
		this.months = months;
		this.days = days;
	}

	public DateSpan clone() {
		return new DateSpan(years, months, days);
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}
	
	public int getDays() {
		return days;
	}
	
	public void setDays(int days) {
		this.days = days;
	}
	
    public boolean before(MutableDate date) {
        MutableDate refDate = resolve(new Date());
        return refDate.before(date);
    }

    public boolean after(MutableDate date) {
        MutableDate refDate = resolve(new Date());
        return refDate.after(date);
    }

    public MutableDate resolve(Date base) {
        return new MutableDate(
            base.getYear()  + years + 1900,
            base.getMonth() + months,
            base.getDate()  + days
        );
    }

    @Override
    public MutableDate resolve(MutableDate base) {
        return new MutableDate(
            base.getYear()  + years,
            base.getMonth() + months,
            base.getDay()  + days
        );
    }

	@Override
	public int hashCode() {
		return years * 10000 + months * 100 + days;	
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o != null && o instanceof DateSpan) {
			DateSpan span = (DateSpan) o;
			return 	years  == span.years && 
					months == span.months &&
					days   == span.days;
		}
		return false;
	}

}
