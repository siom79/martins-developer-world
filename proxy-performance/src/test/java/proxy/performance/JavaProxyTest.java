package proxy.performance;

import org.junit.Test;

public class JavaProxyTest {

    @Test
    public void testPerformance() {
        final IExample example = JavaProxy.createExample();
        long measure = TimeMeasurement.measure(new TimeMeasurement.Execution() {
            @Override
            public void execute() {
                for (long i = 0; i < JavassistProxyTest.NUMBER_OF_ITERATIONS; i++) {
                    example.setName("name");
                }
            }
        });
        System.out.println("Proxy: "+measure+" ms");
    }
}
