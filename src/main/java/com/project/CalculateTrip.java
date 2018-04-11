package com.project;

import com.itextpdf.text.Document;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class CalculateTrip {
    private String descript;
    private String leaveTime;
    private String arriveTime;
    private String totalTime;
    private Double dietCost;
    private String breakfastAmount;
    private String dinnerAmount;
    private String supperAmount;
    private Double freeFoodCost;
    private Double totalDietCost;
    private Double dietValue;
    private String transType;
    private Double ticketPrice;
    private Double ovCcm;
    private Double unCcm;
    private Double motorcycle;
    private Double motBicycle;
    private Double trvlCost;
    private String lumpSum;
    private Double sleepBill;
    private Double lump;
    private String pLumpSum;
    private Double returnPay;
    private Double pLump;
    private Double advance;
    private Double payment;
    private Double sumCosts;
    private List<String> costs;
    private List<String> amounts;
    private List<Double> dbAmounts;
    private Double otherExpensesSum;


    private Trip trip;
    public CalculateTrip(Trip trip) throws ParseException {
        this.trip = trip;
        description();
        totalTripTime();
        dishes();
        diet(dietCost, freeFoodCost);
        transportType();
        tcktPrice();
        travelCosts();
        accommodation();
        localCommunication();
        otherExpenses();
        score(dietValue, ticketPrice, trvlCost, lump, sleepBill, pLump, returnPay, otherExpensesSum);
    }

    private void description() {
        this.descript = trip.getDescr();
    }

    private void totalTripTime() throws ParseException {
        String totalTime;
        Double dietCost;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String start = trip.getLeaveDate() + " " + trip.getLeaveTime();
        String end = trip.getArriveDate() + " " + trip.getArriveTime();
        DateTime dStart = new DateTime(format.parse(start));
        DateTime dEnd = new DateTime(format.parse(end));
        totalTime = Days.daysBetween(dStart, dEnd).getDays() + "d ";
        totalTime += Hours.hoursBetween(dStart, dEnd).getHours() % 24 + "h ";
        totalTime += Minutes.minutesBetween(dStart, dEnd).getMinutes() % 60 + "m ";
        dietCost = Double.valueOf(Days.daysBetween(dStart, dEnd).getDays()) * Double.valueOf(trip.getPay()) +
                Double.valueOf(Hours.hoursBetween(dStart, dEnd).getHours() % 24 ) * (Double.valueOf(trip.getPay()) / 24) +
                Double.valueOf(Minutes.minutesBetween(dStart, dEnd).getMinutes() % 60) * ((Double.valueOf(trip.getPay()) / 24) / 60);
        this.dietCost = dietCost;
        this.leaveTime = start;
        this.arriveTime = end;
        this.totalTime = totalTime;
    }

    private void dishes() throws ParseException {
        String breakfastAmount;
        String dinnerAmount;
        String supperAmount;
        Double freeFoodCost;

        breakfastAmount = trip.getBreakfast();
        dinnerAmount = trip.getDinner();
        supperAmount = trip.getSupper();

        freeFoodCost = (Integer.parseInt(breakfastAmount) * 7.5) + (Integer.parseInt(dinnerAmount) * 15.0) +
                (Integer.parseInt(supperAmount) * 7.5);

        this.breakfastAmount = breakfastAmount;
        this.dinnerAmount = dinnerAmount;
        this.supperAmount = supperAmount;
        this.freeFoodCost = freeFoodCost;
    }

    private void diet(Double dietCost, Double freeFoodCost) {
        Double dietValue;
        dietValue = dietCost - freeFoodCost;
        this.dietValue = dietValue;
    }

    private void transportType() {
        String transType;
        transType = trip.getTransType();
        this.transType = transType;
    }

    private void tcktPrice() {
        Double ticketPrice;
        ticketPrice = Double.valueOf(trip.getTicketPrice());
        this.ticketPrice = ticketPrice;
    }

    private void travelCosts() {
        //<900 1km = 0.52
        //>900 1km = 0,84
        //motocykl 1km = 0,23
        //motorower 1km = 0,14
        Double unCcm;
        Double ovCcm;
        Double motorcycle;
        Double motBicycle;
        Double trvlCost;

        unCcm = Double.valueOf(trip.getUnCcm());
        ovCcm = Double.valueOf(trip.getOvCcm());
        motorcycle = Double.valueOf(trip.getMotorcycle());
        motBicycle = Double.valueOf(trip.getMotBicycle());

        trvlCost = unCcm * 0.52 + ovCcm * 0.84 + motorcycle * 0.23 + motBicycle * 0.14;

        this.unCcm = unCcm;
        this.ovCcm = ovCcm;
        this.motorcycle = motorcycle;
        this.motBicycle = motBicycle;
        this.trvlCost = trvlCost;
    }

    private void accommodation() {
        String lumpSum;
        Double lump;
        Double sleepBill;

        lumpSum = trip.getLumpSum();
        lump = Integer.parseInt(lumpSum) * 45.0;
        sleepBill = Double.valueOf(trip.getSleepBill());

        this.lumpSum = lumpSum;
        this.sleepBill = sleepBill;
        this.lump = lump;
    }

    private void localCommunication() {
        String pLumpSum;
        Double pLump;
        Double returnPay;

        pLumpSum = trip.getpLumpSum();
        pLump = Integer.parseInt(pLumpSum) * 6.0;
        returnPay = Double.valueOf(trip.getReturnPay());

        this.pLumpSum = pLumpSum;
        this.pLump = pLump;
        this.returnPay = returnPay;
    }

    private void otherExpenses() {
        List<String> costs;
        List<String> amounts;
        Double otherExpensesSum = 0.0;
        List<Double> dbAmounts = new ArrayList<Double>();

        costs = trip.getCosts();
        amounts = trip.getAmounts();

        for(String amnts : amounts){
            dbAmounts.add(Double.valueOf(amnts));
        }

        for(Double othExpSum : dbAmounts){
            otherExpensesSum += othExpSum;
        }

        this.otherExpensesSum = otherExpensesSum;
        this.costs = costs;
        this.amounts = amounts;
        this.dbAmounts = dbAmounts;
    }

    private void score(Double dietValue, Double ticketPrice, Double trvlCost, Double lump, Double sleepBill,
                       Double pLump, Double returnPay, Double otherExpensesSum) {
        Double sumCosts;
        Double advance;
        Double payment;

        sumCosts = dietValue + ticketPrice + trvlCost + lump + sleepBill + pLump + returnPay + otherExpensesSum;
        advance = Double.valueOf(trip.getAdvance());
        payment = sumCosts - advance;

        this.sumCosts = sumCosts;
        this.payment = payment;
        this.advance = advance;
    }
}
