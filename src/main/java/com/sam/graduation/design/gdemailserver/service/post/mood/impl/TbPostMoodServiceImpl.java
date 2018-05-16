package com.sam.graduation.design.gdemailserver.service.post.mood.impl;

import com.sam.graduation.design.gdemailserver.controller.dto.TbPostMoodDTO;
import com.sam.graduation.design.gdemailserver.controller.dto.TbUserDTO;
import com.sam.graduation.design.gdemailserver.dao.TbPostMoodMapper;
import com.sam.graduation.design.gdemailserver.dao.TbUserMapper;
import com.sam.graduation.design.gdemailserver.model.pojo.TbPostMood;
import com.sam.graduation.design.gdemailserver.model.pojo.TbUser;
import com.sam.graduation.design.gdemailserver.service.base.BaseService;
import com.sam.graduation.design.gdemailserver.service.post.mood.TbPostMoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbPostMoodServiceImpl extends BaseService implements TbPostMoodService {

    private static final String FILE_SEPARATOR = File.separator;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Value("${url.link.path}")
    private String urlLinkPath;

    @Autowired
    private TbPostMoodMapper tbPostMoodMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public List<TbPostMoodDTO> getTbPostMood() {

        List<TbPostMoodDTO> tbPostMoodDTOS = new ArrayList<>();

        List<TbPostMood> tbPostMoods = this.tbPostMoodMapper.selectAll();
        if (tbPostMoods == null || tbPostMoods.size() == 0) {
            return tbPostMoodDTOS;
        }

        tbPostMoods.stream().forEach(x -> {
            TbPostMoodDTO tbPostMoodDTO= new TbPostMoodDTO();
            tbPostMoodDTO.from(x);
            tbPostMoodDTO.setImageurl(urlLinkPath + FILE_SEPARATOR + x.getImageurl());

            TbUserDTO tbUserDTO = new TbUserDTO();
            TbUser tbUser = this.tbUserMapper.selectByPrimaryKey(x.getUserid());
            tbUserDTO.from(tbUser);
            tbUserDTO.setImage(urlLinkPath + FILE_SEPARATOR + tbUser.getImage());

            tbPostMoodDTO.setTbUserDTO(tbUserDTO);

            tbPostMoodDTOS.add(tbPostMoodDTO);
        });

        return tbPostMoodDTOS;
    }
}
