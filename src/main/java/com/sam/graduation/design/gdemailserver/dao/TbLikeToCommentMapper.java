package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbLikeToComment;

public interface TbLikeToCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbLikeToComment record);

    int insertSelective(TbLikeToComment record);

    TbLikeToComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbLikeToComment record);

    int updateByPrimaryKey(TbLikeToComment record);
}