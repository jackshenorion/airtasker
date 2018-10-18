package shared.date;

import util.shared.delegate.Action;

public class StopWatch {

	public static StopWatch start() {
		return new StopWatch();
	}

    public static int run(Action action) {
        StopWatch stopWatch = new StopWatch();
        action.run();
        return stopWatch.get();
    }

    private long _initialStart;
	private long _start;

	private StopWatch() {
		_initialStart = _start = System.currentTimeMillis();
	}

	public int get() {
		return (int)(System.currentTimeMillis() - _start);
	}

	public int getAndReset() {
		int elapsed = (int)(System.currentTimeMillis() - _start);
		reset();
		return elapsed;
	}

    public void reset() {
        _start = System.currentTimeMillis();
    }

	public int total() {
		return (int)(System.currentTimeMillis() - _initialStart);
	}
}
