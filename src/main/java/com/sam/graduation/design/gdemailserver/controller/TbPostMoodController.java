package com.sam.graduation.design.gdemailserver.controller;

import com.sam.graduation.design.gdemailserver.constvalue.ServiceResultType;
import com.sam.graduation.design.gdemailserver.controller.base.BaseController;
import com.sam.graduation.design.gdemailserver.controller.dto.TbPostMoodDTO;
import com.sam.graduation.design.gdemailserver.dao.TbPostMoodMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbPostMood;
import com.sam.graduation.design.gdemailserver.service.post.mood.TbPostMoodService;
import com.sam.graduation.design.gdemailserver.utils.GDMSFileUtils;
import com.sam.graduation.design.gdemailserver.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api("动态相关")
@RestController
public class TbPostMoodController extends BaseController {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Value("${url.link.path}")
    private String urlLinkPath;

    @Autowired
    private TbPostMoodMapper tbPostMoodMapper;

    @Resource
    private TbPostMoodService tbPostMoodService;

    @ApiOperation("动态发布相关")
    @PostMapping("/tb/post/mood/@insert")
    public Map<String, Object> tbPostMoodInsrt(
            @RequestParam("userId") Long userId,
            @RequestParam("text") String text,
            @RequestParam("file") MultipartFile file
    ) {
        String imageOraName = file.getOriginalFilename();
        String imageFormat = imageOraName.toLowerCase().substring(imageOraName.lastIndexOf("."), imageOraName.length())
                .toLowerCase();
        String imageRelPath = "video" + FILE_SEPARATOR + "image" + FILE_SEPARATOR + GDMSFileUtils.getTimePath()
                + FILE_SEPARATOR + userId + FILE_SEPARATOR + UUIDUtil.getUUIDWithoutLine() + imageFormat;

        File headImageFile = Paths.get(fileRootPath, imageRelPath).toFile();

        try {
            FileUtils.copyToFile(file.getInputStream(), headImageFile);
        } catch (IOException e) {
            logger.error("e:{}.", e);
        }

        Date now  = new Date();
        TbPostMood tbPostMood = new TbPostMood();
        tbPostMood.setContent(text);
        tbPostMood.setCreattime(now);
        tbPostMood.setImageurl(imageRelPath);
        tbPostMood.setUserid(userId);

        int result = 0;

        try {
            result = this.tbPostMoodMapper.insertSelective(tbPostMood);
        } catch (Exception e) {
            logger.error("e:{}",e);
        }

        if (result == 0) {
            return this.error("发布失败", ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success("发布成功");

    }

    @ApiOperation("获取动态相关")
    @GetMapping("/tb/mood/@get")
    public Map<String, Object> getTbMood() {
        List<TbPostMoodDTO> tbPostMoodDTOS = this.tbPostMoodService.getTbPostMood();
        if (tbPostMoodDTOS == null|| tbPostMoodDTOS.size() == 0){
            return this.success(new ArrayList<>());
        }
        return this.success(tbPostMoodDTOS);
    }


}
