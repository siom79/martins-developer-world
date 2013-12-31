package proxy.performance;

import org.junit.Test;

public class JavassistProxyTest {
    public static final int NUMBER_OF_ITERATIONS = 100000000;

    @Test
    public void testPerformance() throws InstantiationException, IllegalAccessException {
        final IExample example = JavassistProxy.createExample();
        long measure = TimeMeasurement.measure(new TimeMeasurement.Execution() {
            @Override
            public void execute() {
                for (long i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                    example.setName("name");
                }
            }
        });
        System.out.println("javaassist: "+measure+" ms");
    }
}
