package shared.date;

import java.util.Date;


public class MutablePeriod {
	
	private MutableDate start;
	private MutableDate end;
	
	public MutablePeriod(MutableDate start, MutableDate end) {
		this.start = start;
		this.end = end;
	}
	
	public MutableDate getStart() {
		return start;
	}
	
	public Date getStartDate() {
		return start.toDate();
	}
	
	public MutableDate getEnd() {
		return end;
	}
	
	public Date getEndDate() {
		return end.toDate();
	}
	
}
