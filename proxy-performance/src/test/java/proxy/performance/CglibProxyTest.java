package proxy.performance;

import org.junit.Test;

public class CglibProxyTest {

    @Test
    public void testPerformance() {
        final IExample example = CglibProxy.createExample();
        long measure = TimeMeasurement.measure(new TimeMeasurement.Execution() {
            @Override
            public void execute() {
                for (long i = 0; i < JavassistProxyTest.NUMBER_OF_ITERATIONS; i++) {
                    example.setName("name");
                }
            }
        });
        System.out.println("cglib: "+measure+" ms");
    }
}
