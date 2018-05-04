package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbCollection;

public interface TbCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbCollection record);

    int insertSelective(TbCollection record);

    TbCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbCollection record);

    int updateByPrimaryKey(TbCollection record);
}