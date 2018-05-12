package com.project.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "additional_costs")
public class AdditionalCost {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "adc_id")
    private int id;
    
    @Column(name = "adc_cost")
    private String adcCost;
    
    @Column(name = "adc_amount")
    private String adcAmount;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_id", nullable = false)
    private Report report;
    
    public AdditionalCost(String adcCost, String adcAmount, Report report) {
    	this.adcCost = adcCost;
    	this.adcAmount = adcAmount;
    	this.report = report;
    }

    public AdditionalCost(){

    }
}
