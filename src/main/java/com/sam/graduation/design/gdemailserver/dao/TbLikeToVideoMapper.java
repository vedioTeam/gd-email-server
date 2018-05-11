package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbLikeToVideo;
import org.apache.ibatis.annotations.Param;

public interface TbLikeToVideoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbLikeToVideo record);

    int insertSelective(TbLikeToVideo record);

    TbLikeToVideo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbLikeToVideo record);

    int updateByPrimaryKey(TbLikeToVideo record);

    TbLikeToVideo selectByVideoIdAndUserId(
            @Param("userid") Long userId,
            @Param("videoid") Long videoid
    );

    int deleteByUserIdAndVideoId(
            @Param("userId") Long userId,
            @Param("videoId") Long videoId
    );

}