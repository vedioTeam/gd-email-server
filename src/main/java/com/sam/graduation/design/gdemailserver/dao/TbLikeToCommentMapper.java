package com.sam.graduation.design.gdemailserver.dao;

import com.sam.graduation.design.gdemailserver.model.pojo.TbLikeToComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbLikeToCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbLikeToComment record);

    int insertSelective(TbLikeToComment record);

    TbLikeToComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbLikeToComment record);

    int updateByPrimaryKey(TbLikeToComment record);

    List<TbLikeToComment> selectByCommentId(Long commentid);

    TbLikeToComment selectByCommentAndUserId(
            @Param("commentid") Long commentid,
            @Param("userid") Long userid
    );

    int deleteByUserIdAndCommentId(
            @Param("userId") Long userId,
            @Param("commentId") Long commentId
    );

}