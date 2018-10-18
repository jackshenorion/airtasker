package shared.schedule;

@FunctionalInterface
public interface AsyncRepeatingAction {

	void run(AsyncRepeatingActionCallback callback);

}
