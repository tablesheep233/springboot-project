package org.table.neweims.entities;

import org.table.neweims.enums.NatureEnum;

public class Track {
    private Integer id;

    private String year;

    private String employee;

    private NatureEnum nature;

    private String job;

    private int money;

    private String city;

    private String industry;

    private String session;

    private String detail;

    private Integer studentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee == null ? null : employee.trim();
    }

    public NatureEnum getNature() {
        return nature;
    }

    public void setNature(String nature) {
        NatureEnum natureEnum = NatureEnum.getNature(nature);
        if (natureEnum==null){
            this.nature = NatureEnum.valueOf(nature);
        }else {
            this.nature = natureEnum;
        }
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}