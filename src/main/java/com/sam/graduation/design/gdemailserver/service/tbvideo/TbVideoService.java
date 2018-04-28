package com.sam.graduation.design.gdemailserver.service.tbvideo;

import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;

import java.util.List;

public interface TbVideoService {

    List<TbVideo> selectVideo(Integer pageNo);

}
