package org.table.neweims.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.mapper.EnterpriseMapper;
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.util.MyResult;

@Service("enterpriseService")
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;


    @Override
    public void addEnterprise(Enterprise enterprise) {
        Session session = SecurityUtils.getSubject().getSession();
        enterprise.setStatus(0);
        enterprise.setUserId((Integer) session.getAttribute("loginUser"));
        enterpriseMapper.insertEnterprise(enterprise);
    }

    @Override
    public void editEnterprise(Enterprise enterprise) {
        enterpriseMapper.updataEnterprise(enterprise);
    }

    /**
     * 判断企业是否通过审核
     * @param username
     * @return
     */
    @Override
    public MyResult isLegalEnterprise(String username) {
        MyResult myResult = new MyResult();
        Integer status = enterpriseMapper.selectEnterpriseStatus(username);
        if (status==null){
            myResult.setResult("enterprise:please");
        }else if (status==0){
            myResult.setResult("enterprise:illegal");
        }else {
            myResult.setResult("enterprise:legal");
        }
        return myResult;
    }

    @Override
    public MyResult getEnterpriseByUser(Integer userId) {
        Enterprise enterprise = enterpriseMapper.selectEnterpriseByUser(userId);
        if (enterprise!=null){
            return new MyResult(true,enterprise);
        }else {
            return new MyResult(false,null);
        }
    }


}
