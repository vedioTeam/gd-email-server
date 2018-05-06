package com.sam.graduation.design.gdemailserver.service.user.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.TbUserDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.controller.pub.AppException;
import com.sam.graduation.design.gdemailserver.dao.TbUserMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbUser;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.user.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

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
            logger.error("e:{}",e);
        }

        if (result == 0 ) {
            messageDTO.setSuccess(false);
            messageDTO.setMessage("修改信息失败");
            return messageDTO;
        }

        messageDTO.setSuccess(true);
        messageDTO.setMessage("更新信息成功");
        return messageDTO;
    }
}
