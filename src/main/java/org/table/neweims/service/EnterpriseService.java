package org.table.neweims.service;

import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.util.MyResult;

import java.util.List;

public interface EnterpriseService {

    void addEnterprise(Enterprise enterprise);

    void editEnterprise(Enterprise enterprise);

    MyResult isLegalEnterprise(String username);

    MyResult getEnterpriseByUser(Integer userId);

    Page<EnterpriseDto> getAllEnterprise(String status, String search, Integer currPage);

    Enterprise getEnterpriseById(Integer id);

    Integer getEnterpriseCountBy(StatusEnum statusEnum);
}
