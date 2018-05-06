package com.sam.graduation.design.gdemailserver.service.user;

import com.sam.graduation.design.gdemailserver.controller.dto.TbUserDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 19:01:27
 */
public interface TbUserService {

    TbUserDTO userLogin(TbUserDTO tbUserDTO);

    MessageDTO userRegister(TbUserDTO tbUserDTO);

    MessageDTO userUpdate(TbUserDTO tbUserDTO);

}
