package com.project.Repositories;

import com.project.Models.Report;
import com.project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository  extends JpaRepository<Report, Long> {
    Report findReportById(int id);
    List<Report> findByUserId(int id);
}
