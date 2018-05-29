package com.project.Models;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter@Setter
public class FilterAjaxRespBody {
    private String msg;
    private List<User> data;
}
