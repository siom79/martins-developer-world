package proxy.performance;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JavassistProxy {

    public static IExample createExample() throws IllegalAccessException, InstantiationException {
        Class aClass = createClass();
        final IExample newInstance = (IExample) aClass.newInstance();
        MethodHandler methodHandler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method overridden, Method proceed, Object[] args) throws Throwable {
                return proceed.invoke(self, args);
            }
        };
        ((ProxyObject)newInstance).setHandler(methodHandler);
        return newInstance;
    }

    private static Class createClass() {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(Example.class);
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method method) {
                boolean returnValue = false;
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for(Annotation[] annotations : parameterAnnotations) {
                    for(Annotation annotation : annotations) {
                        if(annotation instanceof NotNull) {
                            returnValue = true;
                        }
                    }
                }
                return returnValue;
            }
        });
        return factory.createClass();
    }
}
