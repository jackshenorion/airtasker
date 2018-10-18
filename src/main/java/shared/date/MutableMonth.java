package shared.date;

import java.util.Date;

public final class MutableMonth implements Comparable<MutableMonth> {

	private int _year;
    private int _month;
    private int _serial;
	
    public MutableMonth() {
    	this(new Date());
    }
	
    public MutableMonth(int year, int month) {
	    _year = year;
	    _month = month;		
	    normalise();
    }
	
    public MutableMonth(Date date) {
    	this(date.getYear() + 1900, date.getMonth()); 
    }
    
    public MutableMonth(MutableDate date) {
    	this(date.getYear(), date.getMonth());
    }

    private MutableMonth(int year, int month, int serial) {
	    _year = year;
	    _month = month;
	    _serial = serial;
    }
    
    public MutableMonth clone() {
	    return new MutableMonth(_year, _month, _serial);
    }

    public int compareTo(MutableMonth m) {
        if (m == null) {
            return 1;
        }
        if (m == this) {
            return 0;
        }
        return _serial - m._serial;
    }

    public boolean before(MutableMonth m) {
	    return _serial < m._serial;
    }
	
    public boolean after(MutableMonth m) {
	    return _serial > m._serial;
    }
	
    public MutableMonth addMonths(int months) {
	    _month += months;
	    normalise();
	    return this;
    }
	
    public MutableMonth addYears(int years) {
	    _year += years;
	    normalise();
	    return this;
    }
    
    public int getYear() {
        return _year; 
    }
    
    public MutableMonth setYear(int years) {
        _year = years; 
        normalise(); 
        return this;
    }

    public int getMonth() {
        return _month; 
    }
    
    public MutableMonth setMonth(int months) {
        _month = months; 
        normalise();
        return this;
    }

    public MutableDate getFirstDay() {
        return new MutableDate(_year, _month, 1);
    }

    public MutableDate getLastDay() {
        return new MutableDate(_year, _month, DateUtil.getDaysOfMonth(this));
    }

    @Override
    public int hashCode() {
	    return _serial;
    }

    public boolean equals(MutableMonth m) {
	    return _serial == m._serial;
    }

    public boolean equals(Object o) {
	    if (o == this) return true;
	    if (o != null && o instanceof MutableMonth) {
            MutableMonth m = (MutableMonth) o;
		    return _serial == m._serial;
	    }
	    return false;
    }

    public String toString() {
        return _year + "-" + (_month + 1);
    }

    /**
     * This method may contains some boilerplate code that is to help it run as
     * fast as it can.
     */
    private void normalise() {

	    // normalise month
	    if (_month > 11 || _month < 0) {
		    normaliseMonth();
	    }
	    _serial = generateUniqueKey(_year, _month);
    }
	
    private static int generateUniqueKey(int y, int m) {
	    return y * 10000 + m * 100;
    }

    private int normaliseMonth() {
	    int m = _month;
	    if (m > 11) {
		    int delta = m % 12;
		    _year += m / 12;
		    m = _month = delta;
	    }
	    else if (m < 0) {
		    int delta = m % 12;
		    _year += m / 12;
		    if (delta != 0) {
			    _year--;
			    m = _month = 12 + delta;
		    }
		    else {
			    m = _month = 0;
		    }
	    }
	    return m;
    }
        
}
