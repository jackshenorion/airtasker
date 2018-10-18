package shared.date;

import util.shared.collection.IsInt;

import java.util.Date;

public class MutableTime implements Cloneable, Comparable<MutableTime>, IsInt {
    
    private int hour;
    private int minute;
    private int second;
    private int intValue;
    
    public MutableTime() {
        this(new Date());
    }
    
    public MutableTime(Date date) {
        this(date.getHours(), date.getMinutes(), date.getSeconds());
    }

    public MutableTime(int hour, int minute, int second) {
        this.hour = hour % 24;
        this.minute = minute % 60;
        this.second = second % 60;
        intValue = toInt(this);
    }

    private MutableTime(int hour, int minute, int second, int intValue) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.intValue = intValue;
    }

    public boolean equals(MutableTime d) {
        return intValue == d.intValue;
    }

    public MutableTime clone() {
        return new MutableTime(hour, minute, second, intValue);
    }

    public int compareTo(MutableTime t) {
        if (t == null) {
            return 1;
        }
        if (t == this) {
            return 0;
        }
        return intValue - t.intValue;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public int toInt() {
        return intValue;
    }

    public Date toDate() {
		Date date = new Date();
		date.setHours(hour);
		date.setMinutes(minute);
		date.setSeconds(second);
		return date;
	}

    public static int toInt(MutableTime time) {
        return time.getHour() * 10000 + time.getMinute() * 100 + time.getSecond();
    }

}
