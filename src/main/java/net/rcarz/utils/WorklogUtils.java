package net.rcarz.utils;

import net.rcarz.jiraclient.WorkLog;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * Created by mariusmerkevicius on 1/30/16.
 * A set of utils static methods help set {@link WorkLog}
 */
public class WorklogUtils {

    /**
     * Formats duration time into pretty string format
     * Does not output seconds
     *
     * @param durationInSeconds provided duration to format
     * @return formatted duration
     */
    public static String formatDurationFromSeconds(long durationInSeconds) {
        if (durationInSeconds < 60)
            return "0m";
        StringBuilder builder = new StringBuilder();
        PeriodType type = PeriodType.forFields(new DurationFieldType[]{
                DurationFieldType.hours(),
                DurationFieldType.minutes()
        });

        Period period = new Period(1000 * durationInSeconds, type);
        if (period.getHours() != 0)
            builder.append(period.getHours()).append("h").append(" ");
        if (period.getMinutes() != 0)
            builder.append(period.getMinutes()).append("m").append(" ");
        if ((builder.length() > 0) && builder.charAt(builder.length() - 1) == " ".charAt(0))
            builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
