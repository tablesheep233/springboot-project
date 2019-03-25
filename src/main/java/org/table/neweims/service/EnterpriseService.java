package org.table.neweims.service;

import org.springframework.web.multipart.MultipartFile;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.util.MyResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EnterpriseService {

    void addEnterprise(Enterprise enterprise);

    void editEnterprise(Enterprise enterprise);

    List<String> isLegalEnterprise(String username);

    MyResult getEnterpriseByUser(Integer userId);

    Page<EnterpriseDto> getAllEnterprise(StatusEnum statu, String search, Integer currPage);

    Enterprise getEnterpriseById(Integer id);

    Integer getEnterpriseCountBy(StatusEnum statusEnum);

    public Map<String,Object> upload(MultipartFile uploadImgFile) throws IOException;
}
