package com.sam.graduation.design.gdemailserver.service.user.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.HomePageVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbUserDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.controller.pub.AppException;
import com.sam.graduation.design.gdemailserver.dao.TbFriendsMapper;
import com.sam.graduation.design.gdemailserver.dao.TbUserMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbFriends;
import com.sam.graduation.design.gdemailserver.model.pojo.TbUser;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.user.TbUserService;
import com.sam.graduation.design.gdemailserver.service.video.TbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 19:02:21
 */
@Service
public class TbUserServiceImpl extends BaseService implements TbUserService {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${url.link.path}")
    private String urlLinkPath;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbFriendsMapper tbFriendsMapper;

    @Resource
    private TbVideoService tbVideoService;

    @Override
    public TbUserDTO userLogin(TbUserDTO tbUserDTO) {

        TbUserDTO userDTO = null;

        TbUser user = tbUserDTO.to();

        TbUser tbUser = this.tbUserMapper.selectByUserNameAndPassword(user);
        if (tbUser == null) {
            return userDTO;
        }
        userDTO = new TbUserDTO();
        userDTO.from(tbUser);
        userDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());
        return userDTO;
    }

    @Override
    @Transactional
    public MessageDTO userRegister(TbUserDTO tbUserDTO) {

        MessageDTO messageDTO = new MessageDTO();

        TbUser user = tbUserDTO.to();

        TbUser tbUser = this.tbUserMapper.selectByUsername(tbUserDTO.getUsername());
        if (tbUser != null) {
            messageDTO.setSuccess(false);
            messageDTO.setMessage("已经被注册");
            return messageDTO;
        }

        user.setCtm(new Date());
        int result = 0;

        try {
            result = this.tbUserMapper.insertSelective(user);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("注册异常");
        }

        if (result == 0) {
            messageDTO.setMessage("注册失败");
            messageDTO.setSuccess(false);
            return messageDTO;
        }

        messageDTO = new MessageDTO();
        messageDTO.setSuccess(true);
        messageDTO.setMessage("注册成功");
        return messageDTO;
    }

    @Override
    @Transactional
    public MessageDTO userUpdate(TbUserDTO tbUserDTO) {

        MessageDTO messageDTO = new MessageDTO();

        int result = 0;

        try {
            result = this.tbUserMapper.updateByPrimaryKeySelective(tbUserDTO.to());
        } catch (Exception e) {
            logger.error("e:{}", e);
        }

        if (result == 0) {
            messageDTO.setSuccess(false);
            messageDTO.setMessage("修改信息失败");
            return messageDTO;
        }

        messageDTO.setSuccess(true);
        messageDTO.setMessage("更新信息成功");
        return messageDTO;
    }

    @Override
    public MessageDTO deleteByUsererIdAndUseredId(Long usererId, Long useredId) {
        MessageDTO messageDTO = null;

        int result = 0;
        try {
            result = this.tbFriendsMapper.deleteByUsererIdAndUseredId(usererId, useredId);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("取消关注异常");
        }

        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setSuccess(false);
            messageDTO.setMessage("取消关注错误");
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setSuccess(true);
        messageDTO.setMessage("取消关注成功");
        return messageDTO;
    }

    @Override
    public MessageDTO focusUser(Long usererId, Long useredId) {

        MessageDTO messageDTO = null;

        int result = 0;

        TbFriends tbFriends = new TbFriends();
        tbFriends.setFocustime(new Date());
        tbFriends.setUseredid(useredId);
        tbFriends.setUsererid(usererId);

        try {
            result = this.tbFriendsMapper.insert(tbFriends);
        } catch (Exception e) {
            logger.error("e:{}", e);
            throw new AppException("关注异常");
        }

        if (result == 0) {
            messageDTO = new MessageDTO();
            messageDTO.setMessage("关注错误");
            messageDTO.setSuccess(false);
            return messageDTO;
        }
        messageDTO = new MessageDTO();
        messageDTO.setSuccess(true);
        messageDTO.setMessage("关注成功");
        return messageDTO;
    }

    @Override
    public TbUserDTO getUserDetail(Long userId, Long otherUserId) {

        TbUserDTO tbUserDTO = new TbUserDTO();

        TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(otherUserId);

        tbUserDTO.from(tbUser);
        tbUserDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());

        List<HomePageVideoDTO> createsList = this.tbVideoService.getOtherUsersHomePageVideo(userId, otherUserId);
        tbUserDTO.setCreateHomePageVideoDTOS(createsList);

        List<HomePageVideoDTO> collectList = this.tbVideoService.getCollectsHomePageVideo(userId, otherUserId);
        tbUserDTO.setCollectHomePageVideoDTOS(collectList);

        List<TbFriends> tbFocusers = this.tbFriendsMapper.selectByUsererId(otherUserId);

        List<TbFriends> tbFocuseds = this.tbFriendsMapper.selectByUseredId(otherUserId);

        tbUserDTO.setFocusers(tbFocusers.size());
        tbUserDTO.setFocuseds(tbFocuseds.size());

        TbFriends tbFriends = this.tbFriendsMapper.selectByUsererIdAndUseredId(otherUserId, userId);
        if (tbFriends == null) {
            tbUserDTO.setFocus(false);
        } else {
            tbUserDTO.setFocus(true);
        }

        return tbUserDTO;
    }


}
