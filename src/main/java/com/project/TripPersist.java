package com.project;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TripPersist {
    private CalculateTrip calculateTrip;
    private Trip trip;

    @Getter private HashMap<String, String> generalMap = new LinkedHashMap<>();
    @Getter private HashMap<String, List> additionalMap = new LinkedHashMap<>();

    public TripPersist(CalculateTrip calculateTrip, Trip trip){
        this.calculateTrip = calculateTrip;
        this.trip = trip;

        fillGeneralMap();
        fillAdditionalMap();
    }

    private void fillGeneralMap(){
        generalMap.put("Opis", calculateTrip.getDescript());
        generalMap.put("Data i godzina wyjazdu", calculateTrip.getLeaveTime());
        generalMap.put("Data i godzina powrotu", calculateTrip.getArriveTime());
        generalMap.put("Calkowity czas podrozy", calculateTrip.getTotalTime());
        generalMap.put("Wysokosc diet", calculateTrip.getDietCost().toString());
        generalMap.put("Ilosc sniadan", String.valueOf(calculateTrip.getBreakfastAmount()));
        generalMap.put("Ilosc obiadow", String.valueOf(calculateTrip.getDinnerAmount()));
        generalMap.put("Ilosc kolacji", String.valueOf(calculateTrip.getSupperAmount()));
        generalMap.put("Kwota zmniejszajaca diety", String.valueOf(calculateTrip.getFreeFoodCost()));
//        generalMap.put("Loczna wysokosc diet", calculateTrip.getTotalDietCost().toString());
        generalMap.put("Rodzaj srodka transportu", calculateTrip.getTransType());
        generalMap.put("Koszty przejazdu wg biletu", calculateTrip.getTicketPrice().toString());
        generalMap.put("Przejazd samochodem do 900ccm", calculateTrip.getUnCcm().toString());
        generalMap.put("Przejazd samochodem pow 900ccm", calculateTrip.getOvCcm().toString());
        generalMap.put("Przejazd motocyklem", calculateTrip.getMotorcycle().toString());
        generalMap.put("Przejazd motorowerem", calculateTrip.getMotBicycle().toString());
        generalMap.put("Koszty rozliczane km", calculateTrip.getTrvlCost().toString());
        generalMap.put("Liczba noclegow - ryczalt", String.valueOf(calculateTrip.getLumpSum()));
        generalMap.put("Przyslugujacy ryczalt", calculateTrip.getLump().toString());
        generalMap.put("Koszt noclegow", calculateTrip.getSleepBill().toString());
        generalMap.put("Ilosc dob - ryczalt", String.valueOf(calculateTrip.getPLumpSum()));
        generalMap.put("Ryczalt za dojazdy", calculateTrip.getPLump().toString());
        generalMap.put("Koszty dojazdu", calculateTrip.getReturnPay().toString());
        generalMap.put("Suma kosztow", calculateTrip.getSumCosts().toString());
        generalMap.put("Pobrana zaliczka", calculateTrip.getAdvance().toString());
        generalMap.put("Do wplaty", calculateTrip.getPayment().toString());
    }

    private void fillAdditionalMap(){
        additionalMap.put("Costs", trip.getCosts());
        additionalMap.put("Amounts", trip.getAmounts());
    }
}
