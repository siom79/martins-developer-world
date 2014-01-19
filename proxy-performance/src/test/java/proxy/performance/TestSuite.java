package proxy.performance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({NoProxyTest.class, JavaProxyTest.class, JavassistProxyTest.class, CglibProxyTest.class})
public class TestSuite {
}
