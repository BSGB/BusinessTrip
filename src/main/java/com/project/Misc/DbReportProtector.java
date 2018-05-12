package com.project.Misc;

import com.project.Models.Report;
import com.project.Models.User;
import lombok.Getter;

import java.util.Set;

public class DbReportProtector {
    private User user;
    private Integer reportId;
    private Set<Report> userReports;
    @Getter
    private Report report;

    public DbReportProtector(User user, Integer reportId) {
        this.user = user;
        this.reportId = reportId;
        userReports = this.user.getReports();
    }

    public boolean isOwner(){
        for(Report rp : userReports){
            if(rp.getId() == reportId) {
                report = rp;
                return true;
            }
        }
        return false;
    }
}
