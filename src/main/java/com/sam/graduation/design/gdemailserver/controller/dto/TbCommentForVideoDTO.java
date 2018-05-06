package com.sam.graduation.design.gdemailserver.controller.dto;

import com.sam.graduation.design.gdemailserver.model.pojo.TbCommentForVideo;

import java.util.Date;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 21:07:18
 */
public class TbCommentForVideoDTO {

    private Long id;

    private Long videoid;

    private Long userid;

    private String content;

    private String commenttime;

    public TbCommentForVideo to() {
        TbCommentForVideo tbCommentForVideo = new TbCommentForVideo();
        tbCommentForVideo.setId(this.id);
//        tbCommentForVideo.setCommenttime(new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }
}
