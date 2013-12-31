package proxy.performance;

public class TimeMeasurement {

    public interface Execution {
        public void execute();
    }

    public static long measure(Execution execution) {
        long start = System.currentTimeMillis();
        execution.execute();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
