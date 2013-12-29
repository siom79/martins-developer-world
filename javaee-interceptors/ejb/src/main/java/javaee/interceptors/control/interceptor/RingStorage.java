package javaee.interceptors.control.interceptor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class RingStorage implements PerformanceResourceMXBean {
    private final long[] measurements;
    private int currentPosition = 0;
    private String id;

    public RingStorage(int numberOfMeasurements, String id) {
        this.id = id;
        measurements = new long[numberOfMeasurements];
        for (int i = 0; i < measurements.length; i++) {
            measurements[i] = -1;
        }
        registerMBean();
    }

    private void registerMBean() {
        try {
            ObjectName objectName = new ObjectName("performance" + id + ":type=" + this.getClass().getName());
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            try {
                platformMBeanServer.unregisterMBean(objectName);
            } catch (Exception e) {
            }
            platformMBeanServer.registerMBean(this, objectName);
        } catch (Exception e) {
            throw new IllegalStateException("Problem during registration of Monitoring into JMX:" + e);
        }
    }

    public void addMeasurement(long time) {
        measurements[currentPosition] = time;
        currentPosition++;
        currentPosition = currentPosition % measurements.length;
    }

    public long getMeanValue() {
        int numberOfValues = 0;
        long sum = 0;
        for (int i = 0; i < measurements.length; i++) {
            long value = measurements[i];
            if (value != -1) {
                sum += value;
                numberOfValues++;
            }
        }
        long meanValue = 0;
        if (numberOfValues > 0) {
            meanValue = sum / numberOfValues;
        }
        return meanValue;
    }

    @Override
    public long getPerformanceMeasurements() {
        return getMeanValue();
    }
}
