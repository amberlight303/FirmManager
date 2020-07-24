package com.amberlight.firmmanager.util;

import com.amberlight.firmmanager.model.TimeCounter;
import java.util.Date;

/**
 * An accessory class for calculating time differences
 * between passed dates (times) and current date (time).
 */
public class TimeAnalyzer {

    /**
     * Calculate a difference of years between current
     * time and passed date as a parameter.
     * @param date the date to calculate difference
     * @return the number of years as a difference between current and passed date
     */
    public long analyzeYears(Date date){
        long unixTime = date.getTime() / 1000;
        long currentUnixTime = System.currentTimeMillis() / 1000;
        long years = (currentUnixTime - unixTime) / 31556926;
        return years;
    }

    /**
     * Calculate a difference of days between current
     * time and passed date as a parameter.
     * @param date the date to calculate difference
     * @return the number of days as a difference between current and passed date
     */
    public long analyzeDays(Date date){
        long unixTime = date.getTime() / 1000;
        long currentUnixTime = System.currentTimeMillis() / 1000;
        long days = (currentUnixTime - unixTime) / 86400;
        return days;
    }

    /**
     * Calculate and return days passed since certain time point.
     * {@link TimeCounter} has start time point and old value of days passed since the start time point.
     * If the days difference between current time and start time point greater than old days value
     * of <code>TimeCounter</code>'s field, then return this days difference, otherwise return 0.
     * @param timeCounter the object with start time point and days from start point values
     * @return days passed since certain time point
     */
    public long hasDayPassed(TimeCounter timeCounter){
        long currentDaysFromStart = this.analyzeDays(timeCounter.getStartDate());
        long daysFromStartDB = timeCounter.getDaysFromStart();
        if (currentDaysFromStart > daysFromStartDB) {
            return currentDaysFromStart;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "TimeAnalyzer{}";
    }
}




