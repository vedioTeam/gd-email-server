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

    private Date commenttime;

    private TbUserDTO tbUserDTO;

    private Integer numberOfFloor;

    private Boolean isLikeComment;

    private Integer numberOfLikeComment;

    public TbUserDTO getTbUserDTO() {
        return tbUserDTO;
    }

    public void setTbUserDTO(TbUserDTO tbUserDTO) {
        this.tbUserDTO = tbUserDTO;
    }

    public Boolean getLikeComment() {
        return isLikeComment;
    }

    public void setLikeComment(Boolean likeComment) {
        isLikeComment = likeComment;
    }

    public Integer getNumberOfLikeComment() {
        return numberOfLikeComment;
    }

    public void setNumberOfLikeComment(Integer numberOfLikeComment) {
        this.numberOfLikeComment = numberOfLikeComment;
    }

    public TbCommentForVideo to() {
        TbCommentForVideo tbCommentForVideo = new TbCommentForVideo();
        tbCommentForVideo.setId(this.id);
        tbCommentForVideo.setCommenttime(new Date());
        tbCommentForVideo.setContent(this.content);
        tbCommentForVideo.setUserid(this.userid);
        tbCommentForVideo.setVideoid(this.videoid);
        return tbCommentForVideo;
    }

    public void from(TbCommentForVideo tbCommentForVideo) {
        this.id = tbCommentForVideo.getId();
        this.videoid = tbCommentForVideo.getVideoid();
        this.userid = tbCommentForVideo.getUserid();
        this.content = tbCommentForVideo.getContent();
        this.commenttime = tbCommentForVideo.getCommenttime();
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

    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }

    public Integer getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(Integer numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }
}
