package org.table.neweims.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.Resume;
import org.table.neweims.mapper.ResumeMapper;
import org.table.neweims.service.ResumeService;

import java.util.List;
import java.util.Map;

@Service("resumeService")
@Transactional
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;


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
            Session session = SecurityUtils.getSubject().getSession();
            resume.setUserId((Integer) session.getAttribute("loginUser"));
            resumeMapper.insertResume(resume);
        } else {
            resumeMapper.updateResume(resume);
        }
    }

    @Override
    public void deleteResume(Integer id) {
        resumeMapper.deleteResume(id);
    }
}
