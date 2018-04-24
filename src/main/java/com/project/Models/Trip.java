package com.project.Models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 06.04.2018.
 */
@Getter@Setter
public class Trip {

    private String descr;
    @NotNull@NotEmpty private String leaveDate;
    @NotNull@NotEmpty private String leaveTime;
    @NotNull@NotEmpty
	private String arriveDate;
    @NotNull@NotEmpty private String arriveTime;
    private Double pay;
    private Integer breakfast;
    private Integer dinner;
    private Integer supper;
    @NotNull private String transType;
    private Double ticketPrice;
    private Double ovCcm;
    private Double unCcm;
    private Double motorcycle;
    private Double motBicycle;
    private Double lumpSum;
    private Double sleepBill;
    private Double pLumpSum;
    private Double returnPay;
    private List<String> costs = new ArrayList<>();
    private List<String> amounts = new ArrayList<>();
    private Double advance;
}
