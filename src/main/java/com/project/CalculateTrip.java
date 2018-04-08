package com.project;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
@Getter@Setter
public class CalculateTrip {
    private String leaveTime;
    private String arriveTime;
    private String totalTime;

    private Trip trip;
    public CalculateTrip(Trip trip) throws ParseException {
        this.trip = trip;
        totalTripTime();
    }

    private void totalTripTime() throws ParseException {
        String totalTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String start = trip.getLeaveDate() + " " + trip.getLeaveTime();
        String end = trip.getArriveDate() + " " + trip.getArriveTime();
        DateTime dStart = new DateTime(format.parse(start));
        DateTime dEnd = new DateTime(format.parse(end));
        totalTime = Days.daysBetween(dStart, dEnd).getDays() + "d ";
        totalTime += Hours.hoursBetween(dStart, dEnd).getHours() % 24 + "h ";
        totalTime += Minutes.minutesBetween(dStart, dEnd).getMinutes() % 60 + "m ";
        this.leaveTime = start;
        this.arriveTime = end;
        this.totalTime = totalTime;
    }
}
