package org.table.neweims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.table.neweims.entities.Track;
import org.table.neweims.mapper.StudentMapper;
import org.table.neweims.mapper.TrackMapper;
import org.table.neweims.service.TrackService;
import org.table.neweims.util.SysContext;

import java.util.List;

@Transactional
@Service("trackService")
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackMapper trackMapper;

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public List<Track> getTrackListFromStu() {
        Integer stuId = studentMapper.selectStuId(SysContext.getCurrentUser());
        List<Track> list = trackMapper.selectTrackListByStu(stuId);
        return list;
    }

    @Override
    public void addOrUpTrack(Track track) {
        if (track.getId()==null){
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
