package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbLikeToUser;

public interface TbLikeToUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbLikeToUser record);

    int insertSelective(TbLikeToUser record);

    TbLikeToUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbLikeToUser record);

    int updateByPrimaryKey(TbLikeToUser record);
}