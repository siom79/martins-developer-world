package proxy.performance;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy {

    public static IExample createExample() {
        final Example example = new Example();
        IExample exampleProxy = (IExample) Enhancer.create(IExample.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                return method.invoke(example, args);
            }
        });
        return exampleProxy;
    }
}
