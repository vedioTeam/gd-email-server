package com.sam.graduation.design.gdemailserver.service.tbvideo.impl;

import com.sam.graduation.design.gdemailserver.dao.TbVideoMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.tbvideo.TbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbVideoServiceImpl extends BaseService implements TbVideoService {

    private static final Integer DEFAULT_PAGESIZE = 20;

    @Autowired
    private TbVideoMapper tbVideoMapper;


    @Override
    public List<TbVideo> selectVideo(Integer pageNo) {
        List<TbVideo> tbVideos = null;

        try {
            tbVideos = this.tbVideoMapper.selectAllTbVideo();
        } catch (Exception e) {
            logger.error("e:{}",e);
        }

        if (tbVideos == null || tbVideos.size() == 0) {
            tbVideos = new ArrayList<TbVideo>();
            return tbVideos;
        }

        List<TbVideo> tbVideoList = tbVideos.subList(pageNo, pageNo + DEFAULT_PAGESIZE);
        return tbVideoList;
    }
}
