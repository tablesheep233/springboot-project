package org.table.neweims.config.ymlpojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "code")
public class Code {

    private int id;

    private String key;

    private int templateId;

    private int resetTemplateId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getResetTemplateId() {
        return resetTemplateId;
    }

    public void setResetTemplateId(int resetTemplateId) {
        this.resetTemplateId = resetTemplateId;
    }
}
