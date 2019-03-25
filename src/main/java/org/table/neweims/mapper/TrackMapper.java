package org.table.neweims.mapper;

import org.apache.ibatis.annotations.*;
import org.table.neweims.entities.Track;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrackMapper {

    List<Track> selectTrackListByStu(Integer stuId);

    void insertTrack(Track track);

    void updateTrack(Track track);

    @Select("select * from tb_track where id=#{id}")
    Track selectTrack(Integer id);

    @Delete("delete from tb_track where id=#{id}")
    void deleteTrack(Integer id);

    List<String> selectCity(@Param("year") String year,@Param("session") Integer session,@Param("major")String major);

    List<Integer> selectCityCount(@Param("year") String year,@Param("session") Integer session,@Param("major")String major);

    @Select("select distinct year from tb_track")
    List<String> selectYear();

    @Select("select distinct session from tb_track")
    List<Integer> selectSession();

    List<Map<String,Object>> selectIndustry(@Param("year") String year,@Param("session") Integer session,@Param("major")String major);

    List<Map<String,Object>> selectMoney(@Param("year") String year,@Param("session") Integer session,@Param("major")String major);
}