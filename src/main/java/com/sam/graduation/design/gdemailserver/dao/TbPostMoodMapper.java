package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbPostMood;

public interface TbPostMoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbPostMood record);

    int insertSelective(TbPostMood record);

    TbPostMood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbPostMood record);

    int updateByPrimaryKey(TbPostMood record);
}