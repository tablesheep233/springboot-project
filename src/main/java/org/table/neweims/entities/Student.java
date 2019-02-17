package org.table.neweims.entities;

import org.table.neweims.enums.GenderEnum;

public class Student {
    private String id;

    private String name;

    private String major;

    private String clazz;

    private GenderEnum gender;

    private String email;

    private String tel;

    private String birth;

    private Integer userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(String gender) {
        GenderEnum genderEnum = GenderEnum.getGender(gender);
        if (genderEnum==null){
            this.gender = GenderEnum.valueOf(gender);
        }else {
            this.gender = genderEnum;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}