package com.project.Misc;

import com.project.Models.Trip;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Getter@Setter
public class CalculateTrip {
    private String descript;
    private String leaveDate;
    private String leaveTime;
    private String arriveDate;
    private String arriveTime;
    private Double pay;
    private Integer breakfastAmount;
    private Integer dinnerAmount;
    private Integer supperAmount;
    private String transType;
    private Double ticketPrice;
    private Double ovCcm;
    private Double unCcm;
    private Double motorcycle;
    private Double motBicycle;
    private Double lumpSum;
    private Double sleepBill;
    private Double pLumpSum;
    private Double returnPay;
    private List<String> costs;
    private List<String> amounts;
    private Double advance;

    //dodatkowe pola
    private String totalTime;
    private Double dietCost;
    private Double freeFoodCost;
    private Double totalDietCost;
    private Double dietValue;
    private Double trvlCost;
    private Double lump;
    private Double pLump;
    private Double payment;
    private Double sumCosts;
    private HashMap<String, String> addCosts = new HashMap<>();

    private List<Double> dbAmounts;
    private Double otherExpensesSum;

    private Trip trip;
    public CalculateTrip(Trip trip) throws ParseException {
        this.trip = trip;

        this.descript = trip.getDescr();
        this.leaveDate = trip.getLeaveDate();
        this.leaveTime = trip.getLeaveTime();
        this.arriveDate = trip.getArriveDate();
        this.arriveTime = trip.getArriveTime();
        this.pay = trip.getPay() == null ? 30 : trip.getPay();
        this.breakfastAmount = trip.getBreakfast() == null ? 0 : trip.getBreakfast();
        this.dinnerAmount = trip.getDinner() == null ? 0 : trip.getDinner();
        this.supperAmount = trip.getSupper() == null ? 0 : trip.getSupper();
        this.transType = trip.getTransType();
        this.ticketPrice = trip.getTicketPrice() == null ? 0.0 : trip.getTicketPrice();
        this.ovCcm = trip.getOvCcm() == null ? 0.0 : trip.getOvCcm();
        this.unCcm = trip.getUnCcm() == null ? 0.0 : trip.getUnCcm();
        this.motorcycle = trip.getMotorcycle() == null ? 0.0 : trip.getMotorcycle();
        this.motBicycle = trip.getMotBicycle() == null ? 0.0 : trip.getMotBicycle();
        this.lumpSum = trip.getLumpSum() == null ? 0.0 : trip.getLumpSum();
        this.sleepBill = trip.getSleepBill() == null ? 0.0 : trip.getSleepBill();
        this.pLumpSum = trip.getPLumpSum() == null ? 0.0 : trip.getPLumpSum();
        this.returnPay = trip.getReturnPay() == null ? 0.0 : trip.getReturnPay();
        this.costs = trip.getCosts();
        this.amounts = trip.getAmounts();
        this.advance = trip.getAdvance() == null ? 0.0 : trip.getAdvance();

        totalTripTime();
        dishes();
        diet(dietCost, freeFoodCost);
        travelCosts();
        accommodation();
        localCommunication();
        otherExpenses();
        score(dietValue, ticketPrice, trvlCost, lump, sleepBill, pLump, returnPay, otherExpensesSum);
        generateAddCosts();
    }

    private void totalTripTime() throws ParseException {
        String totalTime;
        Double dietCost;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String start = leaveDate + " " + leaveTime;
        String end = arriveDate + " " + arriveTime;
        DateTime dStart = new DateTime(format.parse(start));
        DateTime dEnd = new DateTime(format.parse(end));
        totalTime = Days.daysBetween(dStart, dEnd).getDays() + "d ";
        totalTime += Hours.hoursBetween(dStart, dEnd).getHours() % 24 + "h ";
        totalTime += Minutes.minutesBetween(dStart, dEnd).getMinutes() % 60 + "m ";
        dietCost = (double) Days.daysBetween(dStart, dEnd).getDays() * pay +
                (double) (Hours.hoursBetween(dStart, dEnd).getHours() % 24) * (pay / 24) +
                (double) (Minutes.minutesBetween(dStart, dEnd).getMinutes() % 60) * ((pay / 24) / 60);
        this.dietCost = dietCost;
        this.leaveTime = start;
        this.arriveTime = end;
        this.totalTime = totalTime;
    }

    private void dishes() throws ParseException {
        Double freeFoodCost;

        freeFoodCost = (breakfastAmount * 7.5) + (dinnerAmount * 15.0) +
                (supperAmount * 7.5);
        this.freeFoodCost = freeFoodCost;
    }

    private void diet(Double dietCost, Double freeFoodCost) {
        Double dietValue = dietCost - freeFoodCost;
        this.dietValue = dietValue;
    }

    private void travelCosts() {
        //<900 1km = 0.52
        //>900 1km = 0,84
        //motocykl 1km = 0,23
        //motorower 1km = 0,14
        Double trvlCost;
        trvlCost = unCcm * 0.52 + ovCcm * 0.84 + motorcycle * 0.23 + motBicycle * 0.14;
        this.trvlCost = trvlCost;
    }

    private void accommodation() {
        Double lump = lumpSum * 45.0;
        this.lump = lump;
    }

    private void localCommunication() {
        Double pLump = pLumpSum * 6.0;
        this.pLump = pLump;
    }

    private void otherExpenses() {
        Double otherExpensesSum = 0.0;
        List<Double> dbAmounts = new ArrayList<Double>();

        for(String amnts : amounts){
            dbAmounts.add(Double.valueOf(amnts));
        }

        for(Double othExpSum : dbAmounts){
            otherExpensesSum += othExpSum;
        }

        this.otherExpensesSum = otherExpensesSum;
        this.dbAmounts = dbAmounts;
    }

    private void score(Double dietValue, Double ticketPrice, Double trvlCost, Double lump, Double sleepBill,
                       Double pLump, Double returnPay, Double otherExpensesSum) {
        Double sumCosts;
        Double payment;

        sumCosts = dietValue + ticketPrice + trvlCost + lump + sleepBill + pLump + returnPay + otherExpensesSum;
        payment = sumCosts - advance;

        this.sumCosts = sumCosts;
        this.payment = payment;
    }
    
    private void generateAddCosts() {
        Iterator<String> costsIter = costs.iterator();
        Iterator<String> amountsIter = amounts.iterator();

        while(costsIter.hasNext() && amountsIter.hasNext()){
            addCosts.put(costsIter.next(), amountsIter.next());
        }
    }
}
