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
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.service.RecruitmentService;
import org.table.neweims.util.SysContext;

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
    private RoleMapper roleMapper;

    @Autowired
    private PageConf pageConf;

    @Override
    public Page<Recruitment> queryRecruitmentByPage(int currPage,String name) {

        Integer userId = SysContext.getCurrentUser();
        Integer pageSize = pageConf.getRecruitmentLimit();

        if (!roleMapper.selectUserRoleById(userId).equals("enterprise")){
            pageSize = pageConf.getStuRecruitmentLimit();
            userId = null;
        }

        String search = null;

        if (name!=null && !name.equals("")){
            search = "%"+name+"%";
        }

        Map<String, Object> data = new HashedMap();

        data.put("currPage", currPage);
        data.put("pageSize", pageSize);

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
            recruitment.setUserId(SysContext.getCurrentUser());
            recruitmentMapper.insertRecruitment(recruitment);
        }
    }

    @Override
    public void dropRecruitment(Integer id) {
        recruitmentMapper.deleteRecruitment(id);
    }

    @Override
    public void recruitmentStatus(Integer id) {

    }

    @Override
    public Page<RecruitmentDto> queryRecruitmentStatusByPage(int currPage, String name) {
        Integer userId = SysContext.getCurrentUser();
        Integer pageSize = pageConf.getSrLimit();

        if (!roleMapper.selectUserRoleById(userId).equals("enterprise")){
            userId = null;
        }

        String search = null;

        if (name!=null && !name.equals("")){
            search = "%"+name+"%";
        }

        Map<String, Object> data = new HashedMap();

        data.put("currPage", currPage);
        data.put("pageSize", pageSize);

        List<RecruitmentDto> recruitments = recruitmentMapper.getAllSRByPage(userId,search,data);

        Integer count = recruitmentMapper.selectRecritmentCount(userId,search);
        Page<RecruitmentDto> page = new Page<>(currPage,pageSize,count);
        page.setSearch(name);
        page.setResult(recruitments);

        return page;
    }

}
