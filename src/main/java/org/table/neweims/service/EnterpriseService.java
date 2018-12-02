package org.table.neweims.service;

import org.table.neweims.entities.Enterprise;
import org.table.neweims.util.MyResult;

public interface EnterpriseService {

    void addEnterprise(Enterprise enterprise);

    void editEnterprise(Enterprise enterprise);

    MyResult isLegalEnterprise(String username);

    MyResult getEnterpriseByUser(Integer userId);
}
