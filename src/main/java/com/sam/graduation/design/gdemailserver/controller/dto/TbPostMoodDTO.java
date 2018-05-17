package com.sam.graduation.design.gdemailserver.controller.dto;

import com.sam.graduation.design.gdemailserver.model.pojo.TbPostMood;

import java.util.Date;

public class TbPostMoodDTO {

    private Long id;

    private String content;

    private Date creattime;

    private String imageurl;

    private Long userid;

    private TbUserDTO tbUserDTO;

    public void from(TbPostMood tbPostMood) {
        this.id = tbPostMood.getId();
        this.content = tbPostMood.getContent();
        this.creattime = tbPostMood.getCreattime();
        this.imageurl = tbPostMood.getImageurl();
        this.userid = tbPostMood.getUserid();
    }

    public TbUserDTO getTbUserDTO() {
        return tbUserDTO;
    }

    public void setTbUserDTO(TbUserDTO tbUserDTO) {
        this.tbUserDTO = tbUserDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
