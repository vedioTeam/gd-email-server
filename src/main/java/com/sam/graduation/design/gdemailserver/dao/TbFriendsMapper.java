package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbFriends;

import java.util.List;

public interface TbFriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbFriends record);

    int insertSelective(TbFriends record);

    TbFriends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbFriends record);

    int updateByPrimaryKey(TbFriends record);

    List<TbFriends> selectByUsererId(Long usererid);

    List<TbFriends> selectByUseredId(Long useredid);

}