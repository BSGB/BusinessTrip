package com.project.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id")
	private int id;
	
    @Column(name = "report_descr")
    private String reportDescr;
    
    @Column(name = "report_leave_time")
    private String reportLeaveTime;
    
    @Column(name = "report_arrive_time")
    private String reportArriveTime;
    
    @Column(name = "report_total_time")
    private String reportTotalTime;
    
    @Column(name = "report_diet_cost")
    private String reportDietCost;
    
    @Column(name = "report_brkfst_amnt")
    private String reportBrkfstAmnt;
    
    @Column(name = "report_dinner_amnt")
    private String reportDinnerAmnt;
    
    @Column(name = "report_supper_amnt")
    private String reportSupperAmnt;
    
    @Column(name = "report_f_food_cost")
    private String reportFFoodCost;
    
    @Column(name = "report_trans_type")
    private String reportTransType;
    
    @Column(name = "report_tick_price")
    private String reportTickPrice;
    
    @Column(name = "report_un_ccm")
    private String reportUnCcm;
    
    @Column(name = "report_ov_ccm")
    private String reportOvCcm;
    
    @Column(name = "report_motcycle")
    private String reportMotcycle;
    
    @Column(name = "report_motbicycle")
    private String reportMotbicycle;
    
    @Column(name = "report_travel_cost")
    private String reportTravelCost;
    
    @Column(name = "report_lump_sum")
    private String reportLumpSum;
    
    @Column(name = "report_lump")
    private String reportLump;
    
    @Column(name = "report_sleep_bill")
    private String reportSleepBill;
    
    @Column(name = "report_p_lump_sum")
    private String reportPLumpSum;
    
    @Column(name = "report_p_lump")
    private String reportPLump;
    
    @Column(name = "report_return_pay")
    private String reportReturnPay;
    
    @Column(name = "report_sum_cost")
    private String reportSumCost;
    
    @Column(name = "report_advance")
    private String reportAdvance;
    
    @Column(name = "report_payment")
    private String reportPayment;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="report")
	private Set<AdditionalCost> addCosts = new HashSet<>();

	public Report(){

    }
}
