package org.table.neweims.config.ymlpojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "page")
public class PageConf {

    private Integer recruitmentLimit;

    public Integer getRecruitmentLimit() {
        return recruitmentLimit;
    }

    public void setRecruitmentLimit(Integer recruitmentLimit) {
        this.recruitmentLimit = recruitmentLimit;
    }
}
