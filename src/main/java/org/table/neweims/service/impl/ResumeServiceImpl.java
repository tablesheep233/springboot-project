package org.table.neweims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.dto.ApplyResume;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Resume;
import org.table.neweims.enums.GenderEnum;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.mapper.ResumeMapper;
import org.table.neweims.service.ResumeService;
import org.table.neweims.util.CardInfo;
import org.table.neweims.util.SysContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("resumeService")
@Transactional
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private PageConf pageConf;

    @Autowired
    private CardInfo cardInfo;

    @Override
    public List<Map<String, Object>> getResumeList(Integer userId) {

        return resumeMapper.selectListResume(userId);
    }

    @Override
    public Resume getResume(Integer id) {
        return resumeMapper.selectResume(id);
    }

    @Override
    public void saveResume(Resume resume) {
        if (resume.getId() == null){
            resume.setUserId(SysContext.getCurrentUser());
            resumeMapper.insertResume(resume);
        } else {
            resumeMapper.updateResume(resume);
        }
    }

    @Override
    public void deleteResume(Integer id) {
        resumeMapper.deleteResume(id);
    }

    @Override
    public void commitRecruitment(Integer resumeId, Integer recruitmentId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        resumeMapper.insertResumeRecruitment(date,resumeId,recruitmentId);
    }


    @Override
    public Page<Resume> getApplyResume(Integer userId, String search,StatusEnum statusEnum,Integer currPage) {

        Map<String, Object> data = new HashMap<>();
        Integer pageSize = pageConf.getApplyLimit();

        data.put("currPage",currPage);
        data.put("pageSize",pageSize);

        String str = search;

        if (search!=null&&!search.equals("")){
            search = "%"+search+"%";
        }

        List<Map<String,Object>> applyList = resumeMapper.selectApplyResumeByPage(userId,search,statusEnum,data);

        Integer totalCount = resumeMapper.selectApplyResumeCount(userId,search,statusEnum);

        Page<Resume> page = new Page<>(currPage,pageSize,totalCount);

        page.setSearch(str);
        page.setMyResult(applyList);

        return page;
    }

    /**
     * 根据简历id获取具体简历内容
     * @param id
     * @return
     */
    @Override
    public ApplyResume getApplyResumeById(Integer id) {
        ApplyResume applyResume = resumeMapper.selectApplyResumeById(id);
        applyResume.setIntroduce(cardInfo.resumeReplace(applyResume.getIntroduce()));
        applyResume.setSkill(cardInfo.resumeReplace(applyResume.getSkill()));
        applyResume.setExperience(cardInfo.resumeReplace(applyResume.getExperience()));
        applyResume.setGender(GenderEnum.valueOf(applyResume.getGender()).getText());
        return applyResume;
    }

    @Override
    public List<Map<String, Object>> getDeliveryList() {
        return resumeMapper.selectDelivery(SysContext.getCurrentUser());
    }

    @Override
    public void collectResume(Integer id,StatusEnum statusEnum) {
        resumeMapper.updateRecruitmentResume(id, statusEnum);
    }

}
