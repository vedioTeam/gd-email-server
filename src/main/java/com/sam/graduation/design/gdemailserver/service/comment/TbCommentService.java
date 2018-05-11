package com.sam.graduation.design.gdemailserver.service.comment;

import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;

/**
 * @author Created by sam199510.slf(wb-slf388940@alibaba-inc.com)
 * @version 创建时间：2018年05月11日 16:52:02
 */
public interface TbCommentService {

    MessageDTO likeToComment(Long userId, Long commentId);

    MessageDTO unlikeToComment(Long userId, Long commentId);

}
