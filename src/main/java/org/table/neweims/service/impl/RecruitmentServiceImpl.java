package org.table.neweims.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.dto.Page;
import org.table.neweims.dto.RecruitmentDto;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.mapper.RecruitmentMapper;
import org.table.neweims.service.RecruitmentService;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public Page<Recruitment> queryRecruitmentByPage(int currPage,String name) {

        Session session = SecurityUtils.getSubject().getSession();
        Integer userId = (Integer) session.getAttribute("loginUser");

        String search = null;

        if (name!=null && !name.equals("")){
            search = "%"+name+"%";
        }

        Map<String, Object> data = new HashedMap();
        Integer pageSize = pageConf.getRecruitmentLimit();
        data.put("currPage", currPage);
        data.put("pageSize", pageConf.getRecruitmentLimit());

        List<Map<String,Object>> recruitments = recruitmentMapper.getAllRecruitmentByPage(userId,search,data);

        Integer count = recruitmentMapper.selectRecritmentCount(userId,search);
        Page<Recruitment> page = new Page<>(currPage,pageSize,count);
        page.setSearch(name);
        page.setMyResult(recruitments);

        return page;
    }

    @Override
    public Map<String, Object> queryRecruitmentById(Integer id) {

        Map<String, Object> result = recruitmentMapper.queryRecruitmentById(id);

        return result;
    }

    @Override
    public void saveRecruitment(Recruitment recruitment) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        if (recruitment.getId() != null){
            recruitment.setCreateTime(time);
            recruitmentMapper.updateRecruitment(recruitment);
        }else {
            recruitment.setCreateTime(time);
            Session session = SecurityUtils.getSubject().getSession();
            recruitment.setUserId((Integer) session.getAttribute("loginUser"));
            recruitmentMapper.insertRecruitment(recruitment);
        }
    }

    @Override
    public void dropRecruitment(Integer id) {
        recruitmentMapper.deleteRecruitment(id);
    }

}
