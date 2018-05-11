package com.sam.graduation.design.gdemailserver.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Collections2;
import com.sam.graduation.design.gdemailserver.constvalue.ServiceResultType;
import com.sam.graduation.design.gdemailserver.controller.base.BaseController;
import com.sam.graduation.design.gdemailserver.controller.dto.HomePageVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbCommentForVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.service.comment.TbCommentService;
import com.sam.graduation.design.gdemailserver.service.video.TbVideoService;
import com.sam.graduation.design.gdemailserver.utils.GDMSFileUtils;
import com.sam.graduation.design.gdemailserver.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 16:08:57
 */
@Api("视频相关")
@RestController
public class TbVideoController extends BaseController {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Autowired
    private TbVideoService tbVideoService;

    @Autowired
    private TbCommentService tbCommentService;

    @ApiOperation("用户上传视频接口")
    @PostMapping("/tb/video/@upload")
    public Map<String, Object> tbVideoUpload(
            @RequestParam("video") MultipartFile video,
            @RequestParam("image") MultipartFile image,
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("introduce") String introduce
    ) {
        MessageDTO messageDTO = null;

        TbVideoDTO tbVideoDTO = new TbVideoDTO();
        tbVideoDTO.setUserid(userId);
        tbVideoDTO.setVideotitle(title);
        tbVideoDTO.setVideointroduce(introduce);
        tbVideoDTO.setVideowatch((int) (Math.random() * 200));
        tbVideoDTO.setVideocreattime(new Date());


        String videoOraName = video.getOriginalFilename();
        String videoFormat = videoOraName.toLowerCase().substring(videoOraName.lastIndexOf("."), videoOraName.length())
                .toLowerCase();
        String videoRelPath = "video" + FILE_SEPARATOR + "repository" + FILE_SEPARATOR + GDMSFileUtils.getTimePath()
                + FILE_SEPARATOR + userId + FILE_SEPARATOR + UUIDUtil.getUUIDWithoutLine() + videoFormat;

        File videoFile = Paths.get(fileRootPath, videoRelPath).toFile();

        try {
            FileUtils.copyToFile(video.getInputStream(), videoFile);
        } catch (IOException e) {
            logger.error("e:{}", e);
        }

        tbVideoDTO.setVideourl(videoRelPath);

        String imageOraName = image.getOriginalFilename();
        String imageFormat = imageOraName.toLowerCase().substring(imageOraName.lastIndexOf("."), imageOraName.length())
                .toLowerCase();
        String imageRelPath = "video" + FILE_SEPARATOR + "image" + FILE_SEPARATOR + GDMSFileUtils.getTimePath()
                + FILE_SEPARATOR + userId + FILE_SEPARATOR + UUIDUtil.getUUIDWithoutLine() + imageFormat;

        File imageFile = Paths.get(fileRootPath, imageRelPath).toFile();

        try {
            FileUtils.copyToFile(image.getInputStream(), imageFile);
        } catch (IOException e) {
            logger.error("e:{}", e);
        }

        tbVideoDTO.setVideoimage(imageRelPath);

        try {
            messageDTO = this.tbVideoService.uploadVideo(tbVideoDTO);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }

        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);

    }

    @ApiOperation("首页视屏接口")
    @GetMapping("/home/page/videos/@get")
    public Map<String, Object> getHomePageVideos(
            @RequestParam("userId") Long userId
    ) {
        List<HomePageVideoDTO> homePageVideoDTOS = null;

        try {
            homePageVideoDTOS = this.tbVideoService.getHomePageVideo(userId);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }

        if (homePageVideoDTOS == null || homePageVideoDTOS.size() == 0) {
            homePageVideoDTOS = new ArrayList<>();
            return this.success(homePageVideoDTOS);
        }
        return this.success(homePageVideoDTOS);
    }

    @ApiOperation("评论视频")
    @PostMapping("/tb/video/@comment")
    public Map<String, Object> commentTbVideo(
            @RequestBody TbCommentForVideoDTO tbCommentForVideoDTO
    ) {

        MessageDTO messageDTO = null;
        tbCommentForVideoDTO.setCommenttime(new Date());
        try {
            messageDTO = this.tbVideoService.commentVideo(tbCommentForVideoDTO);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }
        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);

    }

    @ApiOperation("获取评论")
    @GetMapping("/tb/video/comment/@get")
    public Map<String, Object> getTbVideoComment(
            @RequestParam("userId") Long userId,
            @RequestParam("videoId") Long videoId
    ) {
        List<TbCommentForVideoDTO> tbCommentForVideoDTOS = new ArrayList<>();

        try {
            tbCommentForVideoDTOS = this.tbVideoService.getTbCommentFoeVideo(userId, videoId);
        } catch (Exception e) {
            logger.error("e:{}.", e);
        }

        if (tbCommentForVideoDTOS == null || tbCommentForVideoDTOS.size() == 0) {
            return this.success(tbCommentForVideoDTOS);
        }
        return this.success(tbCommentForVideoDTOS);

    }

    @ApiOperation("收藏视频")
    @PostMapping("/tb/video/@collect")
    public Map<String, Object> collectTbVideo(
            Long userId,
            Long videoId
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbVideoService.collectionVideo(userId, videoId);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }
        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);
    }

    @ApiOperation("收藏视频")
    @PostMapping("/tb/video/@uncollect")
    public Map<String, Object> unCollectTbVideo(
            Long userId,
            Long videoId
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbVideoService.unCollectionVideo(userId, videoId);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }
        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);
    }

    @ApiOperation("我的收藏视频列表")
    @GetMapping("/tb/video/collect/list/@get")
    public Map<String, Object> tbVideoCollectList(
            Long userId
    ) {
        List<HomePageVideoDTO> homePageVideoDTOS = this.tbVideoService.getCollectsHomePageVideo(userId, userId);
        if (homePageVideoDTOS == null || homePageVideoDTOS.size()==0){
            return this.success(new ArrayList<>());
        }
        return this.success(homePageVideoDTOS);
    }

    @ApiOperation("点赞视频评论")
    @PostMapping("/tb/video/comment/@like")
    public Map<String, Object> likeTbVideoComment(
            Long userId,
            Long commentId
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbCommentService.likeToComment(userId, commentId);
        } catch (Exception e) {
            logger.error("e:{}",e);
        }
        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);
    }

    @ApiOperation("取消点赞视频评论")
    @PostMapping("/tb/video/comment/@like")
    public Map<String, Object> unlikeTbVideoComment(
            Long userId,
            Long commentId
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbCommentService.unlikeToComment(userId, commentId);
        } catch (Exception e) {
            logger.error("e:{}",e);
        }
        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);
    }

}
