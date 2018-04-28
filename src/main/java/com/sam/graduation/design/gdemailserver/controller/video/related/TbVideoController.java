package com.sam.graduation.design.gdemailserver.controller.video.related;

import com.sam.graduation.design.gdemailserver.constvalue.ServiceResultType;
import com.sam.graduation.design.gdemailserver.controller.base.BaseController;
import com.sam.graduation.design.gdemailserver.model.pojo.TbVideo;
import com.sam.graduation.design.gdemailserver.service.tbvideo.TbVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api("视频接口相关")
@RestController
public class TbVideoController extends BaseController {

    @Autowired
    private TbVideoService tbVideoService;

    @ApiOperation("首页视频列表接口")
    @GetMapping("/home/page/videos/@get")
    public Map<String, Object> getHomePageVideos(
            @RequestParam(value = "page_no",required = false) Integer pageNo
    ){

        if (pageNo == null || pageNo < 0) {
            return this.error("页码格式不对", ServiceResultType.RESULT_TYPE_SERVICE_ERROR);
        }

        List<TbVideo> tbVideos = null;
        try {
            tbVideos = this.tbVideoService.selectVideo(pageNo);
        } catch (Exception e) {
            logger.error("e:{}",e);
        }

        return this.success(tbVideos);

    }

}
