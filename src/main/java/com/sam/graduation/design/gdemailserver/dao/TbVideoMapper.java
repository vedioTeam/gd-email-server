package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;

import java.util.List;

public interface TbVideoMapper {
    int deleteByPrimaryKey(Long videoid);

    int insert(TbVideo record);

    int insertSelective(TbVideo record);

    TbVideo selectByPrimaryKey(Long videoid);

    int updateByPrimaryKeySelective(TbVideo record);

    int updateByPrimaryKey(TbVideo record);

    List<TbVideo> selectHomePageVideo();
}