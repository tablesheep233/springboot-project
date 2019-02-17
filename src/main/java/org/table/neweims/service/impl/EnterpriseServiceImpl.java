package org.table.neweims.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.mapper.EnterpriseMapper;
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.util.MyResult;
import org.table.neweims.util.SysContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("enterpriseService")
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private PageConf pageConf;


    @Override
    public void addEnterprise(Enterprise enterprise) {
        enterprise.setUserId(SysContext.getCurrentUser());
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
        String status = enterpriseMapper.selectEnterpriseStatus(username);
        if (status==null||status.equals(StatusEnum.NEED.name())){
            myResult.setResult("enterprise:please");
        }else if (status.equals(StatusEnum.WAIT.name())){
            myResult.setResult("enterprise:illegal");
        }else if (status.equals(StatusEnum.REAL.name())){
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

    @Override
    public Page<EnterpriseDto> getAllEnterprise(String status, String search, Integer currPage) {

        Integer pageSize = pageConf.getEnterpriseLimit();

        Map<String,Object> data = new HashMap<>();
        data.put("currPage",currPage);
        data.put("pageSize",pageSize);

        String test = null;
        if (search!=null&&!search.equals("")){
            test = "%"+search+"%";
        }

        List<EnterpriseDto> list = enterpriseMapper.selectEnterpriseByPage(test,status,data);
        Integer totalCount = enterpriseMapper.selectEnterpriseCount(test,status);

        Page<EnterpriseDto> page = new Page<>(currPage,pageSize,totalCount);
        page.setResult(list);
        page.setSearch(search);

        return page;
    }

    @Override
    public Enterprise getEneeterpriseById(Integer id) {
        return  enterpriseMapper.selectEnterpriseById(id);
    }


}
