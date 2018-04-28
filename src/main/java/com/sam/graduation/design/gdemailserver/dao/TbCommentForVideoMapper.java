package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbCommentForVideo;

public interface TbCommentForVideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCommentForVideo record);

    int insertSelective(TbCommentForVideo record);

    TbCommentForVideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCommentForVideo record);

    int updateByPrimaryKey(TbCommentForVideo record);
}