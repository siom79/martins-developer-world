package javaee.interceptors.control.interceptor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RingStorageFactory {
    private static Map<Method, RingStorage> ringStorageMap = Collections.synchronizedMap(new HashMap<Method, RingStorage>());

    public static RingStorage getRingStorage(Method method) {
        RingStorage ringStorage;
        synchronized (ringStorageMap) {
            ringStorage = ringStorageMap.get(method);
            if (ringStorage == null) {
                ringStorage = new RingStorage(10, method.toGenericString());
                ringStorageMap.put(method, ringStorage);
            }
        }
        return ringStorage;
    }

    public static Map<Method, RingStorage> getAllRingStorages() {
        Map<Method, RingStorage> returnValue;
        synchronized (ringStorageMap) {
            returnValue = new HashMap<Method, RingStorage>(ringStorageMap);
        }
        return returnValue;
    }
}
