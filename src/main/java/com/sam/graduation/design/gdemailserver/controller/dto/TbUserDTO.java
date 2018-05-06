package com.sam.graduation.design.gdemailserver.controller.dto;

import com.sam.graduation.design.gdemailserver.model.pojo.TbUser;

import java.util.Date;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 18:53:38
 */
public class TbUserDTO {

    private Long id;

    private String username;

    private Integer age;

    private Date ctm;

    private Byte sex;

    private String password;

    private String image;

    private Integer focusers;

    private Integer focuseds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCtm() {
        return ctm;
    }

    public void setCtm(Date ctm) {
        this.ctm = ctm;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getFocusers() {
        return focusers;
    }

    public void setFocusers(Integer focusers) {
        this.focusers = focusers;
    }

    public Integer getFocuseds() {
        return focuseds;
    }

    public void setFocuseds(Integer focuseds) {
        this.focuseds = focuseds;
    }

    public TbUser to() {
        TbUser tbUser = new TbUser();

        tbUser.setId(this.id);
        tbUser.setAge(this.age);
        tbUser.setCtm(this.ctm);
        tbUser.setImage(this.image);
        tbUser.setPassword(this.password);
        tbUser.setSex(this.sex);
        tbUser.setUsername(this.username);

        return tbUser;
    }

    public void from(TbUser tbUser) {
        this.age = tbUser.getAge();
        this.ctm = tbUser.getCtm();
        this.id = tbUser.getId();
        this.image = tbUser.getImage();
        this.password = tbUser.getPassword();
        this.sex = tbUser.getSex();
        this.username = tbUser.getUsername();
    }
}
