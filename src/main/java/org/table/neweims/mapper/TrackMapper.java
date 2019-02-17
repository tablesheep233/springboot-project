package org.table.neweims.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.table.neweims.entities.Track;

import java.util.List;

@Mapper
public interface TrackMapper {

    List<Track> selectTrackListByStu(Integer stuId);

    void insertTrack(Track track);

    void updateTrack(Track track);

    @Select("select * from tb_track where id=#{id}")
    Track selectTrack(Integer id);

    @Delete("delete from tb_track where id=#{id}")
    void deleteTrack(Integer id);
}