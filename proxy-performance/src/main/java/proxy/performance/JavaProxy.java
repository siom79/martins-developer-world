package proxy.performance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaProxy {

    public static IExample createExample() {
        final Example example = new Example();
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(example, args);
            }
        };
        return (IExample) Proxy.newProxyInstance(JavaProxy.class.getClassLoader(), new Class[]{IExample.class}, invocationHandler);
    }
}
