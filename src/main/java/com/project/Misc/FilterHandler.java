package com.project.Misc;

import com.project.Models.FilterCriteria;
import com.project.Models.Report;
import com.project.Models.User;
import com.project.Repositories.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FilterHandler {
    private FilterCriteria filterCriteria;
    private UserRepository userRepository;

    public FilterHandler(FilterCriteria filterCriteria, UserRepository userRepository){
        this.filterCriteria = filterCriteria;
        this.userRepository = userRepository;
    }

    public List<User> filterByCompanyName(){
        return userRepository.findAllByCompanyName(filterCriteria.getFilterComName());
    }

    public List<User> filterByDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<User> users = userRepository.findAll();
        Date filterLDate;
        Date filterADate;
        filterLDate = formatter.parse(filterCriteria.getFilterLDate());
        filterADate = formatter.parse(filterCriteria.getFilterADate());

        prepareUsersReportsExtract(formatter, users, filterLDate, filterADate);
        return users;
    }

    public List<User> filterByDateAndCompanyName() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<User> users = userRepository.findAllByCompanyName(filterCriteria.getFilterComName());
        Date filterLDate;
        Date filterADate;
        filterLDate = formatter.parse(filterCriteria.getFilterLDate());
        filterADate = formatter.parse(filterCriteria.getFilterADate());

        prepareUsersReportsExtract(formatter, users, filterLDate, filterADate);
        return users;
    }

    private void prepareUsersReportsExtract(SimpleDateFormat formatter, List<User> users, Date filterLDate, Date filterADate) {
        for (User u : users) {
            for (Iterator<Report> report = u.getReports().iterator(); report.hasNext();) {
                Date reportLDate = new Date();
                Date reportADate = new Date();
                Report currentReport = report.next();

                try {
                    reportLDate = formatter.parse(currentReport.getReportLeaveTime());
                    reportADate = formatter.parse(currentReport.getReportArriveTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!reportLDate.after(filterLDate) || !reportADate.before(filterADate)) {
                    report.remove();
                }
            }
        }
    }
}
