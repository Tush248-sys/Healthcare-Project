package com.example.healthcare;

import jakarta.persistence.Embeddable;

@Embeddable
public class DepartmentConfig {

    private String departmentName;
    private String opdMorningTimings;
    private String opdEveningTimings;
    private String weeklyOffDays;
    private Integer maxPatientsPerShift;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getOpdMorningTimings() {
        return opdMorningTimings;
    }

    public void setOpdMorningTimings(String opdMorningTimings) {
        this.opdMorningTimings = opdMorningTimings;
    }

    public String getOpdEveningTimings() {
        return opdEveningTimings;
    }

    public void setOpdEveningTimings(String opdEveningTimings) {
        this.opdEveningTimings = opdEveningTimings;
    }

    public String getWeeklyOffDays() {
        return weeklyOffDays;
    }

    public void setWeeklyOffDays(String weeklyOffDays) {
        this.weeklyOffDays = weeklyOffDays;
    }

    public Integer getMaxPatientsPerShift() {
        return maxPatientsPerShift;
    }

    public void setMaxPatientsPerShift(Integer maxPatientsPerShift) {
        this.maxPatientsPerShift = maxPatientsPerShift;
    }
}
