package com.project.Misc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.project.Models.AdditionalCost;
import com.project.Models.Report;
import com.project.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDbTranslator {
	
	private CalculateTrip calculateTrip;
	private User user;
	
	public ReportDbTranslator(CalculateTrip calculateTrip, User user) {
		this.calculateTrip = calculateTrip;
		this.user = user;
	}
	
	public Report getReport() {
		Report report = new Report();
		
		report.setReportDescr(calculateTrip.getDescript());
		report.setReportLeaveTime(calculateTrip.getLeaveTime());
		report.setReportArriveTime(calculateTrip.getArriveTime());
		report.setReportTotalTime(calculateTrip.getTotalTime());
		report.setReportDietCost(String.valueOf(calculateTrip.getDietCost()));
		report.setReportBrkfstAmnt(String.valueOf(calculateTrip.getBreakfastAmount()));
		report.setReportDinnerAmnt(String.valueOf(calculateTrip.getDinnerAmount()));
		report.setReportSupperAmnt(String.valueOf(calculateTrip.getSupperAmount()));
		report.setReportFFoodCost(String.valueOf(calculateTrip.getFreeFoodCost()));
		report.setReportTransType(String.valueOf(calculateTrip.getTransType()));
		report.setReportTickPrice(String.valueOf(calculateTrip.getTicketPrice()));
		report.setReportUnCcm(String.valueOf(calculateTrip.getUnCcm()));
		report.setReportOvCcm(String.valueOf(calculateTrip.getOvCcm()));
		report.setReportMotcycle(String.valueOf(calculateTrip.getMotorcycle()));
		report.setReportMotbicycle(String.valueOf(calculateTrip.getMotBicycle()));
		report.setReportTravelCost(String.valueOf(calculateTrip.getTrvlCost()));
		report.setReportLumpSum(String.valueOf(calculateTrip.getLumpSum()));
		report.setReportLump(String.valueOf(calculateTrip.getLump()));
		report.setReportSleepBill(String.valueOf(calculateTrip.getSleepBill()));
		report.setReportPLumpSum(String.valueOf(calculateTrip.getPLumpSum()));
		report.setReportPLump(String.valueOf(calculateTrip.getPLump()));
		report.setReportReturnPay(String.valueOf(calculateTrip.getReturnPay()));
		report.setReportSumCost(String.valueOf(calculateTrip.getSumCosts()));
		report.setReportAdvance(String.valueOf(calculateTrip.getAdvance()));
		report.setReportPayment(String.valueOf(calculateTrip.getPayment()));
		report.setUser(user);

		report.setAddCosts(prepareAddCosts(report));


		return report;
	}
	
	private Set<AdditionalCost> prepareAddCosts(Report report) {
		Set<AdditionalCost> addCosts = new HashSet<>();
		
		for(Map.Entry<String, String> entry: calculateTrip.getAddCosts().entrySet()) {
			addCosts.add(new AdditionalCost(entry.getKey(), entry.getValue(), report));
		}
		return addCosts;
	}

}
