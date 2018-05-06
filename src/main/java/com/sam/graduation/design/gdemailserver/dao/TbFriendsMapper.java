package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbFriends;

public interface TbFriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbFriends record);

    int insertSelective(TbFriends record);

    TbFriends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbFriends record);

    int updateByPrimaryKey(TbFriends record);
}