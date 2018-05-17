package com.sam.graduation.design.gdemailserver.controller.dto;

import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;

import java.util.Date;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 15:54:15
 */
public class TbVideoDTO {

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

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideoimage() {
        return videoimage;
    }

    public void setVideoimage(String videoimage) {
        this.videoimage = videoimage;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
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
        this.videointroduce = videointroduce;
    }

    public Date getVideocreattime() {
        return videocreattime;
    }

    public void setVideocreattime(Date videocreattime) {
        this.videocreattime = videocreattime;
    }

    public Long getUserid() {

        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public TbVideo to() {
        TbVideo tbVideo = new TbVideo();
        tbVideo.setVideoid(this.videoid);
        tbVideo.setUserid(this.userid);
        tbVideo.setVideourl(this.videourl);
        tbVideo.setVideoimage(this.videoimage);
        tbVideo.setVideotitle(this.videotitle);
        tbVideo.setVideowatch(this.videowatch);
        tbVideo.setVideointroduce(this.videointroduce);
        tbVideo.setVideocreattime(new Date());
        return tbVideo;
    }

    public void from(TbVideo tbVideo) {
        this.videoid = tbVideo.getVideoid();
        this.userid = tbVideo.getUserid();
        this.videourl = tbVideo.getVideourl();
        this.videoimage = tbVideo.getVideoimage();
        this.videotitle = tbVideo.getVideotitle();
        this.videowatch = tbVideo.getVideowatch();
        this.videointroduce = tbVideo.getVideointroduce();
        this.videocreattime = tbVideo.getVideocreattime();
    }

    

}
