package com.sam.graduation.design.gdemailserver.service.video;

import com.sam.graduation.design.gdemailserver.controller.dto.HomePageVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbCommentForVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;

import java.util.List;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 15:47:02
 */
public interface TbVideoService {

    MessageDTO uploadVideo(TbVideoDTO tbVideoDTO);

    List<HomePageVideoDTO> getHomePageVideo(Long userId);

    List<HomePageVideoDTO> getOtherUsersHomePageVideo(Long userId, Long otherUserId);

    List<HomePageVideoDTO> getCollectsHomePageVideo(Long userId, Long otherUserId);

    MessageDTO commentVideo(TbCommentForVideoDTO tbCommentForVideoDTO);

    List<TbCommentForVideoDTO> getTbCommentFoeVideo(Long userId, Long videoId);

    MessageDTO collectionVideo(Long userId, Long videoId);

    MessageDTO unCollectionVideo(Long userId, Long videoId);

    MessageDTO likeVideo(Long userId, Long videoId);

    MessageDTO unlikeVideo(Long userId, Long videoId);

}
