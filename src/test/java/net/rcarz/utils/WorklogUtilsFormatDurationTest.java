package net.rcarz.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mariusmerkevicius on 1/30/16.
 */
public class WorklogUtilsFormatDurationTest {

    @Test
    public void testEmpty() throws Exception {
        assertEquals("0m", WorklogUtils.formatDurationFromSeconds(0));
    }

    @Test
    public void testNegative() throws Exception {
        assertEquals("0m", WorklogUtils.formatDurationFromSeconds(-200));
    }

    @Test
    public void testLowSecond() throws Exception {
        assertEquals("0m", WorklogUtils.formatDurationFromSeconds(1));
    }

    @Test
    public void testSeconds() throws Exception {
        assertEquals("0m", WorklogUtils.formatDurationFromSeconds(59));
    }

    @Test
    public void testMinutes() throws Exception {
        assertEquals("1m", WorklogUtils.formatDurationFromSeconds(60));
    }

    @Test
    public void testMinutesAndSeconds() throws Exception {
        assertEquals("1m", WorklogUtils.formatDurationFromSeconds(
                60 // 1 minute
                        + 2) // 2 seconds
        );
    }

    @Test
    public void testMinutesAndSeconds2() throws Exception {
        assertEquals("2m", WorklogUtils.formatDurationFromSeconds(
                60 // 1 minute
                        + 72) // 72 seconds
        );
    }

    @Test
    public void testHours() throws Exception {
        assertEquals("1h 10m", WorklogUtils.formatDurationFromSeconds(
                (60 * 60) // 1 hour
                        + (10 * 60) // 10 minutes
                        + 3) // 3 seconds
        );
    }

    @Test
    public void testDays() throws Exception {
        assertEquals("50h 20m", WorklogUtils.formatDurationFromSeconds(
                (60 * 60 * 50) // 50 hours
                        + (60 * 20) // 20 minutes
                        + (3) // s seconds
        ));
    }

    @Test
    public void testDays2() throws Exception {
        assertEquals("50h 22m", WorklogUtils.formatDurationFromSeconds(
                (60 * 60 * 50) // 50 hours
                        + (60 * 20) // 20 minutes
                        + (125) // 125 seconds
        ));
    }

}