package com.sam.graduation.design.gdemailserver.service.video.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.HomePageVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.controller.pub.AppException;
import com.sam.graduation.design.gdemailserver.dao.TbCollectionMapper;
import com.sam.graduation.design.gdemailserver.dao.TbCommentForVideoMapper;
import com.sam.graduation.design.gdemailserver.dao.TbVideoMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbCollection;
import com.sam.graduation.design.gdemailserver.model.pojo.TbCommentForVideo;
import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.video.TbVideoService;
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

    @Value("url.link.path")
    private String urlLinkPath;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Autowired
    private TbVideoMapper tbVideoMapper;

    @Autowired
    private TbCommentForVideoMapper tbCommentForVideoMapper;

    @Autowired
    private TbCollectionMapper tbCollectionMapper;

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

        if (tbVideos == null|| tbVideos.size()==0) {
            return homePageVideoDTOS;
        }

        for (TbVideo tbVideo: tbVideos) {
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

            homePageVideoDTOS.add(homePageVideoDTO);
        }

        return homePageVideoDTOS;
    }

}
