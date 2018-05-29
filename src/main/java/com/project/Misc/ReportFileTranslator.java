package com.project.Misc;

import com.project.Models.AdditionalCost;
import com.project.Models.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ReportFileTranslator {
    private Report report;
    @Getter @Setter private Map<String, String> generalMap = new LinkedHashMap<>();
    @Getter @Setter private Set<AdditionalCost> addSet = new HashSet<>();
    @Getter @Setter private Map<String, String> addMap = new LinkedHashMap<>();

    public ReportFileTranslator(Report report){
        this.report = report;
        this.addSet = report.getAddCosts();
        fillGeneralMap();
        fillAddMap();
    }

    private void fillGeneralMap(){
        generalMap.put("Opis", report.getReportDescr());
        generalMap.put("Data i godzina wyjazdu", report.getReportLeaveTime());
        generalMap.put("Data i godzina powrotu", report.getReportArriveTime());
        generalMap.put("Calkowity czas podrozy", report.getReportTotalTime());
        generalMap.put("Wysokosc diet", report.getReportDietCost());
        generalMap.put("Ilosc sniadan", report.getReportBrkfstAmnt());
        generalMap.put("Ilosc obiadow", report.getReportDinnerAmnt());
        generalMap.put("Ilosc kolacji", report.getReportSupperAmnt());
        generalMap.put("Kwota zmniejszajaca diety", String.valueOf(report.getReportFFoodCost()));
//        generalMap.put("Laczna wysokosc diet", calculateTrip.getTotalDietCost().toString());
        generalMap.put("Rodzaj srodka transportu", report.getReportTransType());
        generalMap.put("Koszty przejazdu wg biletu", report.getReportTickPrice());
        generalMap.put("Przejazd samochodem do 900ccm", report.getReportUnCcm());
        generalMap.put("Przejazd samochodem pow 900ccm", report.getReportOvCcm());
        generalMap.put("Przejazd motocyklem", report.getReportMotcycle());
        generalMap.put("Przejazd motorowerem", report.getReportMotbicycle());
        generalMap.put("Koszty rozliczane km", report.getReportTravelCost());
        generalMap.put("Liczba noclegow - ryczalt", String.valueOf(report.getReportLumpSum()));
        generalMap.put("Przyslugujacy ryczalt", report.getReportLump());
        generalMap.put("Koszt noclegow", report.getReportSleepBill());
        generalMap.put("Ilosc dob - ryczalt", String.valueOf(report.getReportPLumpSum()));
        generalMap.put("Ryczalt za dojazdy", report.getReportPLump());
        generalMap.put("Koszty dojazdu", report.getReportReturnPay());
        generalMap.put("Suma kosztow", report.getReportSumCost());
        generalMap.put("Pobrana zaliczka", report.getReportAdvance());
        generalMap.put("Do wplaty", report.getReportPayment());
    }

    private void fillAddMap(){
        for(AdditionalCost cost : addSet){
            addMap.put(cost.getAdcCost(), cost.getAdcAmount());
        }
    }
}
