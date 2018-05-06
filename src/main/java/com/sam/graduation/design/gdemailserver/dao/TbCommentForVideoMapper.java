package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbCommentForVideo;

import java.util.List;

public interface TbCommentForVideoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbCommentForVideo record);

    int insertSelective(TbCommentForVideo record);

    TbCommentForVideo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbCommentForVideo record);

    int updateByPrimaryKey(TbCommentForVideo record);

    List<TbCommentForVideo> selectByVideoId(Long videoid);
}