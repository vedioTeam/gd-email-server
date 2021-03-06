package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbPostMood;

import java.util.List;

public interface TbPostMoodMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbPostMood record);

    int insertSelective(TbPostMood record);

    TbPostMood selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbPostMood record);

    int updateByPrimaryKey(TbPostMood record);

    List<TbPostMood> selectAll();

}