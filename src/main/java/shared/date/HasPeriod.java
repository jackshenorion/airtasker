package shared.date;

public final class HasPeriod {
	
	private HasDate start;
	private HasDate end;
	
	protected HasPeriod() {
	}
	
	/**
	 * Create an endless period with a start date.
	 */
	public HasPeriod(HasDate start) {
		this.start = start;
	}

	/**
	 * Create a period with a start date and an end date.
	 */
	public HasPeriod(HasDate start, HasDate end) {
		this(start);
		this.end = end;
	}
	
	public boolean hasEndDate() {
		return end != null;
	}

	public HasDate getStart() {
		return start;
	}
	
	public HasDate getEnd() {
		return end;
	}
	
	public MutableDate resolveStartDate(MutableDate base) {
		return start.resolve(base);
	}

	public MutableDate resolveEndDate(MutableDate base) {
		return (end == null) ? null : end.resolve(base);
	}

}
