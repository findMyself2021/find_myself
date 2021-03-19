package com.findmyself.team.data.service.Traffic;

import com.findmyself.team.data.domain.TrafficVolume;
import com.findmyself.team.data.repository.Traffic.VolumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolumeService {

    private final VolumeRepository volumeRepository;

    public TrafficVolume findOne(String num) {
        return volumeRepository.findOne(num);
    }

    public List<TrafficVolume> findAll() {
        return volumeRepository.findAll();
    }
}
