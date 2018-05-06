package com.sam.graduation.design.gdemailserver.controller;

import com.alibaba.fastjson.JSON;
import com.sam.graduation.design.gdemailserver.constvalue.ServiceResultType;
import com.sam.graduation.design.gdemailserver.controller.base.BaseController;
import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.service.video.TbVideoService;
import com.sam.graduation.design.gdemailserver.utils.GDMSFileUtils;
import com.sam.graduation.design.gdemailserver.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
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
        String imageRelPath = "video"+FILE_SEPARATOR+"image"+ FILE_SEPARATOR + GDMSFileUtils.getTimePath()
                +FILE_SEPARATOR+userId+ FILE_SEPARATOR + UUIDUtil.getUUIDWithoutLine() + imageFormat;

        File imageFile = Paths.get(fileRootPath , imageRelPath).toFile();

        try {
            FileUtils.copyToFile(image.getInputStream(), imageFile);
        } catch (IOException e) {
            logger.error("e:{}",e);
        }

        tbVideoDTO.setVideoimage(imageRelPath);

        try {
            messageDTO = this.tbVideoService.uploadVideo(tbVideoDTO);
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
