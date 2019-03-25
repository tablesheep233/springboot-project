package org.table.neweims.service;

import org.table.neweims.entities.Track;

import java.util.List;
import java.util.Map;

public interface TrackService {

    List<Track> getTrackListFromStu(Integer stuId);

    void addOrUpTrack(Track track);

    Track getTrackById(Integer id);

    void delete(Integer id);

    Integer getStuId(Integer id);

    Map<String,Object> getCityData(String year,Integer session,String major);

    Map<String,Object> getSerachData();

    List<Map<String,Object>> getIndustry(String year,Integer session,String major);

    List<Map<String,Object>> getMoney(String year,Integer session,String major);
}
