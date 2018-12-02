package org.table.neweims.service;

import org.table.neweims.dto.Page;
import org.table.neweims.entities.Recruitment;


public interface RecruitmentService {

    Page<Recruitment> queryStudentsByPage(int currPage);
}
