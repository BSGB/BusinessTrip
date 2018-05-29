package com.project.Models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SearchCriteria {
	@NotNull@NotEmpty private String searchCrit;
}
