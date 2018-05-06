package com.sam.graduation.design.gdemailserver.controller.dto;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 18:01:20
 */
public class HomePageVideoDTO {

    private TbVideoDTO tbVideoDTO;

    private Integer numberOfComment;

    private Boolean isCollection;

    private Boolean isLike;

    private TbUserDTO tbUserDTO;

    public TbVideoDTO getTbVideoDTO() {
        return tbVideoDTO;
    }

    public void setTbVideoDTO(TbVideoDTO tbVideoDTO) {
        this.tbVideoDTO = tbVideoDTO;
    }

    public Integer getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(Integer numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    public Boolean getCollection() {
        return isCollection;
    }

    public void setCollection(Boolean collection) {
        isCollection = collection;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public TbUserDTO getTbUserDTO() {
        return tbUserDTO;
    }

    public void setTbUserDTO(TbUserDTO tbUserDTO) {
        this.tbUserDTO = tbUserDTO;
    }
}
