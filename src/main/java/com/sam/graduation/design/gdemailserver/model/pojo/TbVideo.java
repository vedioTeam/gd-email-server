package com.sam.graduation.design.gdemailserver.model.pojo;

import java.util.Date;

public class TbVideo {
    private Long videoid;

    private Long userid;

    private String videourl;

    private String videoimage;

    private String videotitle;

    private Integer videowatch;

    private String videointroduce;

    private Date videocreattime;

    public Long getVideoid() {
        return videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl == null ? null : videourl.trim();
    }

    public String getVideoimage() {
        return videoimage;
    }

    public void setVideoimage(String videoimage) {
        this.videoimage = videoimage == null ? null : videoimage.trim();
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle == null ? null : videotitle.trim();
    }

    public Integer getVideowatch() {
        return videowatch;
    }

    public void setVideowatch(Integer videowatch) {
        this.videowatch = videowatch;
    }

    public String getVideointroduce() {
        return videointroduce;
    }

    public void setVideointroduce(String videointroduce) {
        this.videointroduce = videointroduce == null ? null : videointroduce.trim();
    }

    public Date getVideocreattime() {
        return videocreattime;
    }

    public void setVideocreattime(Date videocreattime) {
        this.videocreattime = videocreattime;
    }
}