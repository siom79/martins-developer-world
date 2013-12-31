package proxy.performance;

import org.junit.Test;

public class NoProxyTest {

    @Test
    public void testPerformance() {
        final Example example = new Example();
        long measure = TimeMeasurement.measure(new TimeMeasurement.Execution() {
            @Override
            public void execute() {
                for (long i = 0; i < JavassistProxyTest.NUMBER_OF_ITERATIONS; i++) {
                    example.setName("name");
                }
            }
        });
        System.out.println("No proxy: " + measure + " ms");
    }
}
