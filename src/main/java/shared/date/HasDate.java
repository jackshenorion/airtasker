package shared.date;



public interface HasDate {

	/**
	 * @param base
	 *            - Resolves a date based on the given date as base.
	 */
	public MutableDate resolve(MutableDate base);
	
}
