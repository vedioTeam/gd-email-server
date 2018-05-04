package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbLikeToVideo;

public interface TbLikeToVideoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbLikeToVideo record);

    int insertSelective(TbLikeToVideo record);

    TbLikeToVideo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbLikeToVideo record);

    int updateByPrimaryKey(TbLikeToVideo record);
}