package org.table.neweims.dto;

import org.table.neweims.entities.Enterprise;
import org.table.neweims.entities.Recruitment;

import java.util.List;

public class RecruitmentDto {

    private Enterprise enterprise;

    private List<Recruitment> recruitments;

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<Recruitment> getRecruitments() {
        return recruitments;
    }

    public void setRecruitments(List<Recruitment> recruitments) {
        this.recruitments = recruitments;
    }
}
