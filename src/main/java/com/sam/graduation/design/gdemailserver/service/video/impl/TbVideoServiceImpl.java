package com.sam.graduation.design.gdemailserver.service.video.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.TbVideoDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.message.MessageDTO;
import com.sam.graduation.design.gdemailserver.controller.pub.AppException;
import com.sam.graduation.design.gdemailserver.dao.TbVideoMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.video.TbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 15:47:55
 */
@Service
public class TbVideoServiceImpl extends BaseService implements TbVideoService {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Autowired
    private TbVideoMapper tbVideoMapper;


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


    public static void main(String[] args) {

        System.out.println();

    }

}
