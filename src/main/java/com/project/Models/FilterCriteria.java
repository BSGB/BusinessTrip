package com.project.Models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter@Setter
public class FilterCriteria {
    @NotNull@NotEmpty private String filterCrit;
    @NotNull@NotEmpty private String filterComName;
    @NotNull@NotEmpty private String filterLDate;
    @NotNull@NotEmpty private String filterADate;
}
