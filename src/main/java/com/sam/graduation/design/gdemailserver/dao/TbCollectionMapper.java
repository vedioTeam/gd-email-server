package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbCollection record);

    int insertSelective(TbCollection record);

    TbCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbCollection record);

    int updateByPrimaryKey(TbCollection record);

    TbCollection selectByVideoIdAndUserId(
            @Param("videoid") Long videoid,
            @Param("userid") Long userid
    );

    List<TbCollection> selectByUserId(
            Long userid
    );

    int deleteByUserIdAndCommentId(
            @Param("userId") Long userId,
            @Param("videoId") Long videoId
    );

}