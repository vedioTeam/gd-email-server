package com.sam.graduation.design.gdemailserver.model.pojo;

public class TbVideo {
    private Integer videoid;

    private Integer userid;

    private String videourl;

    private String videoimage;

    private String videotitle;

    public Integer getVideoid() {
        return videoid;
    }

    public void setVideoid(Integer videoid) {
        this.videoid = videoid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
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
}