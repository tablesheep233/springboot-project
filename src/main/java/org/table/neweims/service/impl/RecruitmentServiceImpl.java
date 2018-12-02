package org.table.neweims.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.mapper.RecruitmentMapper;
import org.table.neweims.service.RecruitmentService;

import java.util.List;
import java.util.Map;

@Transactional
@Service("recruitmentService")
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentMapper recruitmentMapper;

    @Autowired
    private PageConf pageConf;

    @Override
    public Page<Recruitment> queryStudentsByPage(int currPage) {

        Map<String, Object> data = new HashedMap();
        Integer pageSize = pageConf.getRecruitmentLimit();
        data.put("currPage", currPage);
        data.put("pageSize", pageSize);

        List<Recruitment> recruitments = recruitmentMapper.getAllRecruitmentByPage(3,data);

        Integer count = recruitmentMapper.selectRecritmentCount(3);
        Page<Recruitment> page = new Page<>(currPage,pageSize,count);
        page.setResult(recruitments);
        return page;
    }
}
