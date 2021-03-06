package com.sam.graduation.design.gdemailserver.service.video.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.HomePageVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbCommentForVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbUserDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.controller.pub.AppException;
import com.sam.graduation.design.gdemailserver.dao.*;
import com.sam.graduation.design.gdemailserver.model.pojo.*;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.video.TbVideoService;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 15:47:55
 */
@Service
public class TbVideoServiceImpl extends BaseService implements TbVideoService {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${url.link.path}")
    private String urlLinkPath;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Autowired
    private TbVideoMapper tbVideoMapper;

    @Autowired
    private TbCommentForVideoMapper tbCommentForVideoMapper;

    @Autowired
    private TbCollectionMapper tbCollectionMapper;

    @Autowired
    private TbLikeToVideoMapper tbLikeToVideoMapper;

    @Autowired
    private TbLikeToCommentMapper tbLikeToCommentMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbFriendsMapper tbFriendsMapper;

    @Override
    @Transactional
    public MessageDTO uploadVideo(TbVideoDTO tbVideoDTO) {

        MessageDTO messageDTO = null;

        TbVideo tbVideo = tbVideoDTO.to();
        tbVideo.setVideowatch((int) (Math.random() * 200));
        tbVideo.setVideocreattime(new Date());

        int result;

        try {
            result = this.tbVideoMapper.insertSelective(tbVideo);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("上传文件错误");
        }

        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("上传失败");
            return messageDTO;
        }

        messageDTO = new MessageDTO();
        messageDTO.setSuccess(true);
        messageDTO.setMessage("上传成功");
        return messageDTO;
    }

    @Override
    public List<HomePageVideoDTO> getHomePageVideo(Long userId) {
        List<HomePageVideoDTO> homePageVideoDTOS = new ArrayList<>();

        List<TbVideo> tbVideos = this.tbVideoMapper.selectHomePageVideo();

        if (tbVideos == null || tbVideos.size() == 0) {
            return homePageVideoDTOS;
        }

        for (TbVideo tbVideo : tbVideos) {
            HomePageVideoDTO homePageVideoDTO = new HomePageVideoDTO();

            TbVideoDTO tbVideoDTO = new TbVideoDTO();
            tbVideoDTO.from(tbVideo);
            tbVideoDTO.setVideourl(urlLinkPath + FILE_SEPARATOR + tbVideo.getVideourl());
            tbVideoDTO.setVideoimage(urlLinkPath + FILE_SEPARATOR + tbVideo.getVideoimage());
            homePageVideoDTO.setTbVideoDTO(tbVideoDTO);

            List<TbCommentForVideo> tbCommentForVideos = this.tbCommentForVideoMapper.selectByVideoId(tbVideo.getVideoid());
            homePageVideoDTO.setNumberOfComment(tbCommentForVideos.size());

            TbCollection tbCollection = this.tbCollectionMapper.selectByVideoIdAndUserId(tbVideo.getVideoid(), userId);
            if (null == tbCollection) {
                homePageVideoDTO.setCollection(false);
            } else {
                homePageVideoDTO.setCollection(true);
            }

            TbLikeToVideo tbLikeToVideo = this.tbLikeToVideoMapper.selectByVideoIdAndUserId(userId, tbVideo.getVideoid());
            if (tbLikeToVideo == null) {
                homePageVideoDTO.setLike(false);
            } else {
                homePageVideoDTO.setLike(true);
            }

            TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(tbVideo.getUserid());
            TbUserDTO tbUserDTO = new TbUserDTO();
            tbUserDTO.from(tbUser);
            tbUserDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());

            List<TbFriends> erFriends = this.tbFriendsMapper.selectByUseredId(tbVideo.getUserid());

            List<TbFriends> edFriends = this.tbFriendsMapper.selectByUsererId(tbVideo.getUserid());

            tbUserDTO.setFocusers(erFriends.size());
            tbUserDTO.setFocuseds(edFriends.size());

            TbFriends tbFriends = this.tbFriendsMapper.selectByUsererIdAndUseredId(userId, tbVideo.getUserid());
            if (tbFriends == null) {
                tbUserDTO.setFocus(false);
            } else {
                tbUserDTO.setFocus(true);
            }

            homePageVideoDTO.setTbUserDTO(tbUserDTO);

            homePageVideoDTOS.add(homePageVideoDTO);
        }

        return homePageVideoDTOS;
    }

    @Override
    public List<HomePageVideoDTO> getOtherUsersHomePageVideo(Long userId, Long otherUserId) {
        List<HomePageVideoDTO> homePageVideoDTOS = new ArrayList<>();

        List<TbVideo> tbVideos = this.tbVideoMapper.selectHomePageVideoByUserId(otherUserId);

        if (tbVideos == null || tbVideos.size() == 0) {
            return homePageVideoDTOS;
        }

        for (TbVideo tbVideo : tbVideos) {
            HomePageVideoDTO homePageVideoDTO = new HomePageVideoDTO();

            TbVideoDTO tbVideoDTO = new TbVideoDTO();
            tbVideoDTO.from(tbVideo);
            tbVideoDTO.setVideourl(urlLinkPath + FILE_SEPARATOR + tbVideo.getVideourl());
            tbVideoDTO.setVideoimage(urlLinkPath + FILE_SEPARATOR + tbVideo.getVideoimage());
            homePageVideoDTO.setTbVideoDTO(tbVideoDTO);

            List<TbCommentForVideo> tbCommentForVideos = this.tbCommentForVideoMapper.selectByVideoId(tbVideo.getVideoid());
            homePageVideoDTO.setNumberOfComment(tbCommentForVideos.size());

            TbCollection tbCollection = this.tbCollectionMapper.selectByVideoIdAndUserId(tbVideo.getVideoid(), userId);
            if (null == tbCollection) {
                homePageVideoDTO.setCollection(false);
            } else {
                homePageVideoDTO.setCollection(true);
            }

            TbLikeToVideo tbLikeToVideo = this.tbLikeToVideoMapper.selectByVideoIdAndUserId(userId, tbVideo.getVideoid());
            if (tbLikeToVideo == null) {
                homePageVideoDTO.setLike(false);
            } else {
                homePageVideoDTO.setLike(true);
            }

            TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(tbVideo.getUserid());
            TbUserDTO tbUserDTO = new TbUserDTO();
            tbUserDTO.from(tbUser);
            tbUserDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());

            List<TbFriends> erFriends = this.tbFriendsMapper.selectByUseredId(tbVideo.getUserid());

            List<TbFriends> edFriends = this.tbFriendsMapper.selectByUsererId(tbVideo.getUserid());

            tbUserDTO.setFocusers(erFriends.size());
            tbUserDTO.setFocuseds(edFriends.size());

            TbFriends tbFriends = this.tbFriendsMapper.selectByUsererIdAndUseredId(userId, tbVideo.getUserid());
            if (tbFriends == null) {
                tbUserDTO.setFocus(false);
            } else {
                tbUserDTO.setFocus(true);
            }

            homePageVideoDTO.setTbUserDTO(tbUserDTO);

            homePageVideoDTOS.add(homePageVideoDTO);
        }

        return homePageVideoDTOS;
    }

    @Override
    public List<HomePageVideoDTO> getCollectsHomePageVideo(Long userId, Long otherUserId) {

        List<HomePageVideoDTO> homePageVideoDTOS = new ArrayList<>();

        List<TbCollection> tbCollections = this.tbCollectionMapper.selectByUserId(otherUserId);

        for (TbCollection tc : tbCollections) {
            HomePageVideoDTO homePageVideoDTO = new HomePageVideoDTO();

            TbVideo tbVideo = this.tbVideoMapper.selectByPrimaryKey(tc.getVideoid());

            TbVideoDTO tbVideoDTO = new TbVideoDTO();
            tbVideoDTO.from(tbVideo);
            tbVideoDTO.setVideourl(urlLinkPath + FILE_SEPARATOR + tbVideo.getVideourl());
            tbVideoDTO.setVideoimage(urlLinkPath + FILE_SEPARATOR + tbVideo.getVideoimage());
            homePageVideoDTO.setTbVideoDTO(tbVideoDTO);

            List<TbCommentForVideo> tbCommentForVideos = this.tbCommentForVideoMapper.selectByVideoId(tbVideo.getVideoid());
            homePageVideoDTO.setNumberOfComment(tbCommentForVideos.size());

            TbCollection tbCollection = this.tbCollectionMapper.selectByVideoIdAndUserId(tbVideo.getVideoid(), userId);
            if (null == tbCollection) {
                homePageVideoDTO.setCollection(false);
            } else {
                homePageVideoDTO.setCollection(true);
            }

            TbLikeToVideo tbLikeToVideo = this.tbLikeToVideoMapper.selectByVideoIdAndUserId(userId, tbVideo.getVideoid());
            if (tbLikeToVideo == null) {
                homePageVideoDTO.setLike(false);
            } else {
                homePageVideoDTO.setLike(true);
            }

            TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(tbVideo.getUserid());
            TbUserDTO tbUserDTO = new TbUserDTO();
            tbUserDTO.from(tbUser);
            tbUserDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());

            List<TbFriends> erFriends = this.tbFriendsMapper.selectByUseredId(tbVideo.getUserid());

            List<TbFriends> edFriends = this.tbFriendsMapper.selectByUsererId(tbVideo.getUserid());

            tbUserDTO.setFocusers(erFriends.size());
            tbUserDTO.setFocuseds(edFriends.size());

            TbFriends tbFriends = this.tbFriendsMapper.selectByUsererIdAndUseredId(userId, tbVideo.getUserid());
            if (tbFriends == null) {
                tbUserDTO.setFocus(false);
            } else {
                tbUserDTO.setFocus(true);
            }

            homePageVideoDTO.setTbUserDTO(tbUserDTO);

            homePageVideoDTOS.add(homePageVideoDTO);
        }

        return homePageVideoDTOS;
    }

    @Override
    @Transactional
    public MessageDTO commentVideo(TbCommentForVideoDTO tbCommentForVideoDTO) {

        MessageDTO messageDTO = new MessageDTO();

        TbCommentForVideo tbCommentForVideo = tbCommentForVideoDTO.to();
        int result = 0;
        try {
            result = this.tbCommentForVideoMapper.insertSelective(tbCommentForVideo);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("评论异常");
        }

        if (result == 0) {
            messageDTO.setSuccess(false);
            messageDTO.setMessage("评论失败");
            return messageDTO;
        }

        messageDTO.setSuccess(true);
        messageDTO.setMessage("评论成功");
        return messageDTO;
    }

    @Override
    public List<TbCommentForVideoDTO> getTbCommentFoeVideo(Long userId, Long videoId) {

        List<TbCommentForVideoDTO> tbCommentForVideoDTOS = new ArrayList<>();

        List<TbCommentForVideo> tbCommentForVideos = this.tbCommentForVideoMapper.selectByVideoId(videoId);
        if (tbCommentForVideos == null || tbCommentForVideos.size() == 0) {
            return tbCommentForVideoDTOS;
        }

        int i = tbCommentForVideos.size();

        for (TbCommentForVideo tbCommentForVideo : tbCommentForVideos) {
            TbCommentForVideoDTO tbCommentForVideoDTO = new TbCommentForVideoDTO();

            tbCommentForVideoDTO.from(tbCommentForVideo);

            TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(tbCommentForVideo.getUserid());
            TbUserDTO tbUserDTO = new TbUserDTO();
            tbUserDTO.from(tbUser);
            tbUserDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());

            tbCommentForVideoDTO.setTbUserDTO(tbUserDTO);

            tbCommentForVideoDTO.setNumberOfFloor(i);

            i--;

            List<TbLikeToComment> tbLikeToComments = this.tbLikeToCommentMapper.selectByCommentId(tbCommentForVideo.getId());
            tbCommentForVideoDTO.setNumberOfLikeComment(tbLikeToComments.size());

            TbLikeToComment tbLikeToComment = this.tbLikeToCommentMapper.selectByCommentAndUserId(tbCommentForVideo.getId(), userId);
            if (tbLikeToComment == null) {
                tbCommentForVideoDTO.setLikeComment(false);
            } else {
                tbCommentForVideoDTO.setLikeComment(true);
            }

            tbCommentForVideoDTOS.add(tbCommentForVideoDTO);

        }

        return tbCommentForVideoDTOS;
    }

    @Override
    public MessageDTO collectionVideo(Long userId, Long videoId) {
        MessageDTO messageDTO = null;

        TbCollection tbCollection = new TbCollection();
        tbCollection.setCollectiontime(new Date());
        tbCollection.setUserid(userId);
        tbCollection.setVideoid(videoId);

        int result = 0;
        try {
            result = this.tbCollectionMapper.insertSelective(tbCollection);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("收藏视频异常");
        }
        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("收藏视频失败");
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setSuccess(true);
        messageDTO.setMessage("收藏视频成功");
        return messageDTO;
    }

    @Override
    public MessageDTO unCollectionVideo(Long userId, Long videoId) {
        MessageDTO messageDTO = null;
        int result = 0;
        try {
            result = this.tbCollectionMapper.deleteByUserIdAndCommentId(userId, videoId);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("取消收藏视频异常");
        }
        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("取消收藏视频失败");
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setMessage("取消收藏视频成功");
        messageDTO.setSuccess(true);
        return messageDTO;
    }

    @Override
    public MessageDTO likeVideo(Long userId, Long videoId) {
        MessageDTO messageDTO = null;

        TbLikeToVideo tbCollection = new TbLikeToVideo();
        tbCollection.setCreattime(new Date());
        tbCollection.setUserid(userId);
        tbCollection.setVideoid(videoId);

        int result = 0;
        try {
            result = this.tbLikeToVideoMapper.insertSelective(tbCollection);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("点赞视频异常");
        }
        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("点赞视频失败");
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setSuccess(true);
        messageDTO.setMessage("点赞视频成功");
        return messageDTO;
    }

    @Override
    public MessageDTO unlikeVideo(Long userId, Long videoId) {
        MessageDTO messageDTO = null;
        int result = 0;
        try {
            result = this.tbLikeToVideoMapper.deleteByUserIdAndVideoId(userId, videoId);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("取消点赞视频异常");
        }
        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("取消点赞视频失败");
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setMessage("取消点赞视频成功");
        messageDTO.setSuccess(true);
        return messageDTO;
    }


}
