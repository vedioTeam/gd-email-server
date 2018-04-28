package com.sam.graduation.design.gdemailserver.model.pojo;

public class TbUser {
    private Integer id;

    private String username;

    private Integer age;

    private String ctm;

    private Byte sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCtm() {
        return ctm;
    }

    public void setCtm(String ctm) {
        this.ctm = ctm == null ? null : ctm.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }
}