package org.table.neweims.entities;

import org.table.neweims.enums.NatureEnum;

public class Track {
    private Integer id;

    private String begin;

    private String end;

    private String employee;

    private NatureEnum nature;

    private String job;

    private Integer studentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin == null ? null : begin.trim();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end == null ? null : end.trim();
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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}