package com.sam.graduation.design.gdemailserver.controller;

import com.sam.graduation.design.gdemailserver.constvalue.ServiceResultType;
import com.sam.graduation.design.gdemailserver.controller.base.BaseController;
import com.sam.graduation.design.gdemailserver.controller.dto.TbUserDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.service.user.TbUserService;
import com.sam.graduation.design.gdemailserver.utils.GDMSFileUtils;
import com.sam.graduation.design.gdemailserver.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 18:52:58
 */
@Api("用户接口相关")
@RestController
public class TbUserController extends BaseController {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Value("${url.link.path}")
    private String urlLinkPath;

    @Autowired
    private TbUserService tbUserService;

    @ApiOperation("注册相关")
    @PostMapping("/tb/user/@register")
    public Map<String, Object> tbUserRegister(
            @RequestBody TbUserDTO tbUserDTO
    ) {
        MessageDTO messageDTO = null;

        tbUserDTO.setImage("");

        try {
            messageDTO = this.tbUserService.userRegister(tbUserDTO);
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

    @ApiOperation("登录相关")
    @PostMapping("/tb/user/@login")
    public Map<String, Object> tbUserLogin(
            @RequestBody TbUserDTO tbUserDTO
    ) {
        TbUserDTO userDTO = null;
        try {
            userDTO = this.tbUserService.userLogin(tbUserDTO);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }
        if (userDTO == null) {
            return this.error("登录失败", ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(userDTO);

    }

    @ApiOperation("修改用户信息相关")
    @PostMapping("/tb/user/info/@update")
    public Map<String, Object> tbUserInfoUpdate(
            @RequestBody TbUserDTO tbUserDTO
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbUserService.userUpdate(tbUserDTO);
        } catch (Exception e) {
            logger.error("e:{}.", e);
        }
        if (messageDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        if (!messageDTO.getSuccess()) {
            return this.error(messageDTO.getMessage(), ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }
        return this.success(messageDTO);
    }

    @ApiOperation("修改用户头像")
    @PostMapping("/tb/user/head/@update")
    public Map<String, Object> tbUserHeadUpdate(
            @RequestParam("headImage") MultipartFile headImage,
            @RequestParam("userId") Long userId
    ) {
        MessageDTO messageDTO = null;

        String imageOraName = headImage.getOriginalFilename();
        String imageFormat = imageOraName.toLowerCase().substring(imageOraName.lastIndexOf("."), imageOraName.length())
                .toLowerCase();
        String imageRelPath = "video" + FILE_SEPARATOR + "image" + FILE_SEPARATOR + GDMSFileUtils.getTimePath()
                + FILE_SEPARATOR + userId + FILE_SEPARATOR + UUIDUtil.getUUIDWithoutLine() + imageFormat;

        File headImageFile = Paths.get(fileRootPath, imageRelPath).toFile();

        try {
            FileUtils.copyToFile(headImage.getInputStream(), headImageFile);
        } catch (IOException e) {
            logger.error("e:{}.", e);
        }
        TbUserDTO tbUserDTO = new TbUserDTO();
        tbUserDTO.setImage(imageRelPath);
        tbUserDTO.setId(userId);

        try {
            messageDTO = this.tbUserService.userUpdate(tbUserDTO);
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

    @ApiOperation("关注用户")
    @PostMapping("/tb/friends/@focus")
    public Map<String, Object> tbFriendsFocus(
            @RequestParam("usererId") Long usererId,
            @RequestParam("useredId") Long useredId
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbUserService.focusUser(usererId, useredId);
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

    @ApiOperation("关注用户")
    @PostMapping("/tb/friends/@unfocus")
    public Map<String, Object> tbFriendsUnFocus(
            @RequestParam("usererId") Long usererId,
            @RequestParam("useredId") Long useredId
    ) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = this.tbUserService.deleteByUsererIdAndUseredId(usererId, useredId);
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

    @ApiOperation("其他用户模块用户详情")
    @GetMapping("/tb/other/user/detail/@get")
    public Map<String, Object> tbOtherUserDetail(
            @RequestParam("userId")  Long userId,
            @RequestParam("otherUserId") Long otherUserId
    ) {
        TbUserDTO tbUserDTO = null;
        try {
            tbUserDTO = this.tbUserService.getUserDetail(userId, otherUserId);
        } catch (Exception e) {
            logger.error("e:{}", e);
        }
        if (tbUserDTO == null) {
            return this.error("系统异常", ServiceResultType.RESULT_TYPE_SYSTEM_ERROR);
        }
        return this.success(tbUserDTO);
    }


}
