package javaee.interceptors.control.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

public class PerformanceInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceInterceptor.class);

    @AroundInvoke
    public Object measureExecutionTime(InvocationContext ctx) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return ctx.proceed();
        } finally {
            long time = System.currentTimeMillis() - start;
            Method method = ctx.getMethod();
            RingStorage ringStorage = RingStorageFactory.getRingStorage(method);
            ringStorage.addMeasurement(time);
            LOGGER.info(method.getDeclaringClass().getCanonicalName() + method.getName() + ": " + time + "; mean: " + ringStorage.getMeanValue());
        }
    }
}
