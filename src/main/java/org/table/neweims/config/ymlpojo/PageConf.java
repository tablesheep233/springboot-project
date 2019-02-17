package org.table.neweims.config.ymlpojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "page")
public class PageConf {

    private Integer recruitmentLimit;

    private Integer stuRecruitmentLimit;

    private Integer applyLimit;

    private Integer enterpriseLimit;

    private Integer srLimit;

    public Integer getRecruitmentLimit() {
        return recruitmentLimit;
    }

    public void setRecruitmentLimit(Integer recruitmentLimit) {
        this.recruitmentLimit = recruitmentLimit;
    }

    public Integer getStuRecruitmentLimit() {
        return stuRecruitmentLimit;
    }

    public void setStuRecruitmentLimit(Integer stuRecruitmentLimit) {
        this.stuRecruitmentLimit = stuRecruitmentLimit;
    }

    public Integer getApplyLimit() {
        return applyLimit;
    }

    public void setApplyLimit(Integer applyLimit) {
        this.applyLimit = applyLimit;
    }

    public Integer getEnterpriseLimit() {
        return enterpriseLimit;
    }

    public void setEnterpriseLimit(Integer enterpriseLimit) {
        this.enterpriseLimit = enterpriseLimit;
    }

    public Integer getSrLimit() {
        return srLimit;
    }

    public void setSrLimit(Integer srLimit) {
        this.srLimit = srLimit;
    }
}
