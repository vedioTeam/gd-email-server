package com.sam.graduation.design.gdemailserver.service.video;

import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 15:47:02
 */
public interface TbVideoService {

    MessageDTO uploadVideo(TbVideoDTO tbVideoDTO);

}
