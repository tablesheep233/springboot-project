package org.table.neweims.service;

import org.table.neweims.entities.Track;

import java.util.List;

public interface TrackService {

    List<Track> getTrackListFromStu();

    void addOrUpTrack(Track track);

    Track getTrackById(Integer id);

    void delete(Integer id);
}
