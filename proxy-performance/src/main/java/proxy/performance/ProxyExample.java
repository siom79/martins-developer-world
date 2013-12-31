package proxy.performance;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.*;import java.lang.Class;import java.lang.Exception;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.System;import java.lang.Throwable;import java.lang.annotation.*;
import java.lang.annotation.Documented;import java.lang.annotation.ElementType;import java.lang.annotation.Retention;import java.lang.annotation.RetentionPolicy;import java.lang.annotation.Target;import java.lang.reflect.Method;

public class ProxyExample {

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExampleAnnotation {

    }

    public static class Example {
        private int number = 42;

        @ExampleAnnotation
        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public static void main(String args[]) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(Example.class);
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method method) {
                boolean returnValue = false;
                if(method.getAnnotation(ExampleAnnotation.class) != null) {
                    returnValue = true;
                }
                return returnValue;
            }
        });
        MethodHandler methodHandler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method overridden, Method proceed, Object[] args) throws Throwable {
                java.lang.System.out.println("MethodHandler: overridden="+overridden.toGenericString()+"; proceed="+proceed.toGenericString()+"; args="+args+"; self="+self.getClass());
                Example example = (Example)self;
                example.setNumber(44);
                return proceed.invoke(self, args);
            }
        };
        try {
            Example newInstance = (Example) factory.create(new Class<?>[0], new Object[0], methodHandler);
            int number = newInstance.getNumber();
            System.out.println(number);
            newInstance.setNumber(43);
            number = newInstance.getNumber();
            System.out.println(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
