package org.table.neweims.service;

import org.table.neweims.entities.Resume;

import java.util.List;
import java.util.Map;

public interface ResumeService {

    List<Map<String,Object>> getResumeList(Integer userId);

    Resume getResume(Integer id);

    void saveResume(Resume resume);

    void deleteResume(Integer id);
}
