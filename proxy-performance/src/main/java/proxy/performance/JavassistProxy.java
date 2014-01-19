package proxy.performance;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JavassistProxy {

    public static IExample createExample() throws IllegalAccessException, InstantiationException {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(Example.class);
        Class aClass = factory.createClass();
        final IExample newInstance = (IExample) aClass.newInstance();
        MethodHandler methodHandler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method overridden, Method proceed, Object[] args) throws Throwable {
                return proceed.invoke(newInstance, args);
            }
        };
        ((ProxyObject)newInstance).setHandler(methodHandler);
        return newInstance;
    }
}
