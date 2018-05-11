package com.sam.graduation.design.gdemailserver.service.comment.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.controller.pub.AppException;
import com.sam.graduation.design.gdemailserver.dao.TbLikeToCommentMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbLikeToComment;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.comment.TbCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Created by sam199510.slf(wb-slf388940@alibaba-inc.com)
 * @version 创建时间：2018年05月11日 16:52:24
 */
@Service
public class TbCommentServiceImpl extends BaseService implements TbCommentService {

    @Autowired
    private TbLikeToCommentMapper tbLikeToCommentMapper;


    @Override
    public MessageDTO likeToComment(Long userId, Long commentId) {

        MessageDTO messageDTO = null;

        TbLikeToComment tbLikeToComment = new TbLikeToComment();
        tbLikeToComment.setCommentid(commentId);
        tbLikeToComment.setCreattime(new Date());
        tbLikeToComment.setUserid(userId);

        int result = 0;
        try {
            result = this.tbLikeToCommentMapper.insertSelective(tbLikeToComment);
        } catch (Exception e) {
            logger.error("e:{}",e);
            throw new AppException("点赞评论异常");
        }

        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("点赞评论失败");
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setMessage("点赞评论成功");
        messageDTO.setSuccess(true);
        return messageDTO;
    }

    @Override
    public MessageDTO unlikeToComment(Long userId, Long commentId) {

        MessageDTO messageDTO = null;

        int result = 0;
        try {
            result = this.tbLikeToCommentMapper.deleteByUserIdAndCommentId(userId, commentId);
        } catch (Exception e) {
            logger.error("e:{}",e);
            throw new AppException("取消点赞评论异常");
        }
        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setMessage("取消点赞评论失败");
            messageDTO.setSuccess(false);
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setMessage("取消点赞评论成功");
        messageDTO.setSuccess(true);
        return messageDTO;
    }
}
