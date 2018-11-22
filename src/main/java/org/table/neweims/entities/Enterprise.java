package org.table.neweims.entities;

public class Enterprise {
    private Integer id;

    private String name;

    private String address;

    private String tel;

    private String email;

    private String imgPath;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}