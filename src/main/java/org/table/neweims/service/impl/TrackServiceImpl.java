package org.table.neweims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.Track;
import org.table.neweims.mapper.StudentMapper;
import org.table.neweims.mapper.TrackMapper;
import org.table.neweims.service.TrackService;
import org.table.neweims.util.SysContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("trackService")
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackMapper trackMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Integer getStuId(Integer id){
        return studentMapper.selectStuId(id);
    }

    @Override
    public Map<String, Object> getCityData(String year, Integer session) {
        Map<String,Object> map = new HashMap<>();
        map.put("city",trackMapper.selectCity(year,session));
        map.put("data",trackMapper.selectCityCount(year,session));
        return map;
    }

    @Override
    public Map<String, Object> getSerachData() {
        Map<String,Object> map = new HashMap<>();
        map.put("years",trackMapper.selectYear());
        map.put("sessions",trackMapper.selectSession());
        return map;
    }

    @Override
    public List<Map<String, Object>> getIndustry(String year, Integer session) {
        return trackMapper.selectIndustry(year,session);
    }

    @Override
    public List<Map<String, Object>> getMoney(String year, Integer session) {
        return trackMapper.selectMoney(year,session);
    }

    @Override
    public List<Track> getTrackListFromStu(Integer stuId) {
        List<Track> list = trackMapper.selectTrackListByStu(stuId);
        return list;
    }

    @Override
    public void addOrUpTrack(Track track) {
        if (track.getId()==null){
            String no = String.valueOf(track.getStudentId());
            track.setSession(no.substring(0,2));
            trackMapper.insertTrack(track);
        }else{
            trackMapper.updateTrack(track);
        }
    }

    @Override
    public Track getTrackById(Integer id) {
        return trackMapper.selectTrack(id);
    }

    @Override
    public void delete(Integer id) {
        trackMapper.deleteTrack(id);
    }
}
