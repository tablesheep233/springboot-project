package org.table.neweims;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.table.neweims.dto.EnterpriseDto;
import org.table.neweims.dto.Page;
import org.table.neweims.dto.RecruitmentDto;
import org.table.neweims.entities.Recruitment;
import org.table.neweims.entities.Student;
import org.table.neweims.entities.Track;
import org.table.neweims.entities.User;
import org.table.neweims.enums.StatusEnum;
import org.table.neweims.mapper.*;
import org.table.neweims.service.RecruitmentService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    RecruitmentMapper recruitmentMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Autowired
    TrackMapper trackMapper;

    @Test
    public void testUser() {

//        List<Map<String,Object>> list = trackMapper.selectIn();
////        for(Map.Entry<String,Integer> entry : map.entrySet()){
////            System.out.println(entry.getKey()+","+entry.getValue());
////        }
//        for (Map<String,Object> l : list) {
//            for(Map.Entry<String,Object> entry : l.entrySet()){
//                System.out.println(entry.getKey()+","+entry.getValue());
//            }
//        }
    }

    @Test
    public void testQuery(){
//        List<String> list = resourceMapper.selectPermsByUsername("table");
//        for (String s:list) {
//            System.out.println(s);
//        }
        Map<String, Object> data = new HashedMap();

        data.put("currPage", 1);
        data.put("pageSize", 3);
        List<RecruitmentDto> list = recruitmentMapper.getAllSRByPage(null,null,data);
        for (RecruitmentDto m:list){
            System.out.println(m.getId());
            System.out.println(m.getReviewer());
        }
//        System.out.println(recruitmentMapper.selectRecritmentCount(6));
    }


    @Test
    public void testStu() {
        Student student = null;
        student =  studentMapper.selectStu(133);
        if (student != null){
            System.out.println(2);
            System.out.println(student.getClazz());
        }
    }

    @Test
    public void testResume(){
        List<Map<String,Object>> list = resumeMapper.selectListResume(1);
        for (Map<String,Object> m : list) {
            System.out.println(m.get("rname"));
        }
    }

    @Test
    public void testTrack(){
        Track track = new Track();
        track.setId(5);
        track.setNature("EMPLOYMENT");
        trackMapper.updateTrack(track);
//        Student student = new Student();
//        student.setId("333");
//        student.setGender("M");
//        student.setName("xxx");
//        student.setMajor("2");
//        student.setBirth("2");
//        student.setClazz("2");
//        studentMapper.insertStudent(student);
    }
}
