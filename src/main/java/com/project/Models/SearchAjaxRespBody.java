package com.project.Models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SearchAjaxRespBody {
	private String msg;
	private List<Report> data;
}
