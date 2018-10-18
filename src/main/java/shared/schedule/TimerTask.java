package shared.schedule;

import util.shared.delegate.Action;
import util.shared.delegate.Func;

public abstract class TimerTask {

    public abstract void cancel();

    public abstract void run(Action task, int delay);

    public abstract void run(RepeatingAction task, int delay);

    public abstract void run(AsyncRepeatingAction task, int initialDelay, int interval);

    /**
     * Create a timer task instance.
     */
    public static TimerTask create() { return provider.run(); }

    /**
     * Initialise implementation provider.
     */
    public static void init(Func<TimerTask> provider) { TimerTask.provider = provider; }
    private static Func<TimerTask> provider;

}
