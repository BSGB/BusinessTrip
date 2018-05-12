package com.project.Misc;

import com.project.Models.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter@Setter
public class ReportResultTranslator {
    private Map<String, String> formValues = new HashMap<>();
    private Map<String, String> additionalValues = new HashMap<>();
    private Report report;

    public ReportResultTranslator(Report report) {
        this.report = report;
        generateFormValues();
        generateAdditionalValues();
    }

    private void generateFormValues() {
        formValues.put("dscrp", report.getReportDescr());
        formValues.put("leave", report.getReportLeaveTime());
        formValues.put("arrive", report.getReportArriveTime());
        formValues.put("total", report.getReportTotalTime());
        formValues.put("diet", report.getReportDietCost());
        formValues.put("breakfast", report.getReportBrkfstAmnt());
        formValues.put("dinner", report.getReportDinnerAmnt());
        formValues.put("supper", report.getReportSupperAmnt());
        formValues.put("freeFood", report.getReportFFoodCost());
        formValues.put("totalDiet", report.getReportDietCost());
        formValues.put("trnsprtType", report.getReportTransType());
        formValues.put("tcktPrice", report.getReportTickPrice());
        formValues.put("underCcm", report.getReportUnCcm());
        formValues.put("overCcm", report.getReportOvCcm());
        formValues.put("motoCycle", report.getReportMotcycle());
        formValues.put("motoBicycle", report.getReportMotbicycle());
        formValues.put("travelCost", report.getReportTravelCost());
        formValues.put("lmpSum", report.getReportLumpSum());
        formValues.put("lmp", report.getReportLump());
        formValues.put("billSleep", report.getReportSleepBill());
        formValues.put("pLmpSum", report.getReportPLumpSum());
        formValues.put("pLmp", report.getReportPLump());
        formValues.put("rtrnPay", report.getReportReturnPay());
        formValues.put("costs", report.getReportSumCost());
        formValues.put("advnc", report.getReportAdvance());
        formValues.put("paymnt", report.getReportPayment());
    }

    public void generateAdditionalValues(){

    }
}
