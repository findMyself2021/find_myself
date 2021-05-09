package com.findmyself.team.data.service.convenient;

import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Life;
import com.findmyself.team.data.domain.convenient.Sport;
import com.findmyself.team.data.repository.convenient.LifeRepository;
import com.findmyself.team.data.repository.convenient.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LifeService {

    private final LifeRepository lifeRepository;

    public Life findOne(Long h_code) {
        return lifeRepository.findOne(h_code);
    }

    public List<Life> findAll() {
        return lifeRepository.findAll();
    }

    public int findMax(){
        return lifeRepository.findMax();
    }

    public int findMin(){
        return lifeRepository.findMin();
    }

    //군집 갯수
    public int findMaxNo(){
        return lifeRepository.findMaxNo();
    }

    public List<Life> findByNo(int no){
        return lifeRepository.findByNo(no);
    }

    public List<ConvenientCluster> findClusters(){

        return lifeRepository.findClusters();
    }

    public ConvenientCluster findClusterByNo(int no){
        return lifeRepository.findClusterByNo(no);
    }
}