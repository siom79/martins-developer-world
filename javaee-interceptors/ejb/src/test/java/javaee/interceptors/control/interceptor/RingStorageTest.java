package javaee.interceptors.control.interceptor;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RingStorageTest {

    @Test
    public void testMeanValue() {
        RingStorage ringStorage = new RingStorage(10, "4711");
        assertThat(ringStorage.getMeanValue(), is(0L));
        ringStorage.addMeasurement(1);
        assertThat(ringStorage.getMeanValue(), is(1L));
        ringStorage.addMeasurement(2);
        assertThat(ringStorage.getMeanValue(), is(1L));
        ringStorage.addMeasurement(3);
        assertThat(ringStorage.getMeanValue(), is(2L));
        ringStorage.addMeasurement(4);
        assertThat(ringStorage.getMeanValue(), is(2L));
        ringStorage.addMeasurement(5);
        assertThat(ringStorage.getMeanValue(), is(3L));
        ringStorage.addMeasurement(6);
        assertThat(ringStorage.getMeanValue(), is(3L));
        ringStorage.addMeasurement(7);
        assertThat(ringStorage.getMeanValue(), is(4L));
        ringStorage.addMeasurement(8);
        assertThat(ringStorage.getMeanValue(), is(4L));
        ringStorage.addMeasurement(9);
        assertThat(ringStorage.getMeanValue(), is(5L));
        ringStorage.addMeasurement(10);
        assertThat(ringStorage.getMeanValue(), is(5L));
        ringStorage.addMeasurement(11);
        assertThat(ringStorage.getMeanValue(), is(6L));
    }
}
