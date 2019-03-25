package org.table.neweims.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.table.neweims.config.ymlpojo.PageConf;
import org.table.neweims.config.ymlpojo.Upload;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.entities.Enterprise;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.exception.MyException;
import org.table.neweims.mapper.EnterpriseMapper;
import org.table.neweims.mapper.RoleMapper;
import org.table.neweims.service.EnterpriseService;
import org.table.neweims.util.CardInfo;
import org.table.neweims.util.MyResult;
import org.table.neweims.util.SysContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private CardInfo cardInfo;

    @Autowired
    private Upload upload;

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
    public List<String> isLegalEnterprise(String username) {
        List<String> list = new ArrayList<>();
        String status = enterpriseMapper.selectEnterpriseStatus(username);
        String isPhoto = enterpriseMapper.selectEnterprisePhoto(username);
        if (isPhoto==null||isPhoto.equals("")){
            list.add("ent:nophoto");
        }else{
            list.add("ent:havephoto");
        }
        if (status==null||status.equals(StatusEnum.NEED.name())){
            list.add("ent:please");
        }else if (status.equals(StatusEnum.WAIT.name())){
            list.add("ent:illegal");
        }else if (status.equals(StatusEnum.REAL.name())){
            list.add("ent:legal");
        }
        return list;
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
    public Page<EnterpriseDto> getAllEnterprise(StatusEnum statu, String search, Integer currPage) {

        Integer pageSize = pageConf.getEnterpriseLimit();

        Map<String,Object> data = new HashMap<>();
        data.put("currPage",currPage);
        data.put("pageSize",pageSize);

        String test = null;
        if (search!=null&&!search.equals("")){
            test = "%"+search+"%";
        }

        List<EnterpriseDto> list = enterpriseMapper.selectEnterpriseByPage(test,statu,data);
        Integer totalCount = enterpriseMapper.selectEnterpriseCount(test,statu);

        Page<EnterpriseDto> page = new Page<>(currPage,pageSize,totalCount);
        page.setResult(list);
        page.setSearch(search);

        return page;
    }

    @Override
    public Enterprise getEnterpriseById(Integer id) {
        Enterprise enterprise = enterpriseMapper.selectEnterpriseById(id);
        enterprise.setIntroduction(cardInfo.resumeReplace(enterprise.getIntroduction()));
        return enterprise;
    }

    @Override
    public Integer getEnterpriseCountBy(StatusEnum statusEnum) {
        return enterpriseMapper.selectEnterpriseCount(null,statusEnum);
    }

    @Override
    public Map<String,Object> upload(MultipartFile uploadImgFile) throws IOException {
        Map<String,Object> map =  new HashMap<>();
        if (uploadImgFile.isEmpty() || StringUtils.isBlank(uploadImgFile.getOriginalFilename())) {
            throw new MyException("文件为空");
        }
        String fileName = uploadImgFile.getOriginalFilename();
        String filePath = upload.getUploadPath() +"/"+ fileName;
        //处理图片
        File dest = new File(filePath);
        //获取路径
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            uploadImgFile.transferTo(dest);
            Enterprise enterprise = new Enterprise();
            enterprise.setId(enterpriseMapper.selectEnterpriseByUser(SysContext.getCurrentUser()).getId());
            enterprise.setImgPath(fileName);
            enterpriseMapper.updataEnterprise(enterprise);
            map.put("msg","成功");
        } catch (IOException e) {
            throw e;
        }
        return map;
    }
}
