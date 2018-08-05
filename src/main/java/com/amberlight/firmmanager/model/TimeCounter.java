package com.amberlight.firmmanager.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Simple JavaBean object representing a time counter.
 * Time counter is necessary for counting days passed from start time point.
 * It's helpful for keeping data always actual.
 */
@Entity
@Table(name = "time_counter")
public class TimeCounter extends BaseEntity{

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "days_from_start")
    private int daysFromStart;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDaysFromStart() {
        return daysFromStart;
    }

    public void setDaysFromStart(int daysFromStart) {
        this.daysFromStart = daysFromStart;
    }

    @Override
    public String toString() {
        return "TimeCounter{" +
                "startDate=" + startDate +
                ", daysFromStart=" + daysFromStart +
                ", id=" + id +
                '}';
    }
}
