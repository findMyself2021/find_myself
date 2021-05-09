package com.findmyself.team.data.service.convenient;

import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Shop;
import com.findmyself.team.data.domain.convenient.Sport;
import com.findmyself.team.data.repository.convenient.ShopRepository;
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
public class SportService {

    private final SportRepository sportRepository;

    public Sport findOne(Long h_code) {
        return sportRepository.findOne(h_code);
    }

    public List<Sport> findAll() {
        return sportRepository.findAll();
    }

    public int findMax(){
        return sportRepository.findMax();
    }

    public int findMin(){
        return sportRepository.findMin();
    }

    //군집 갯수
    public int findMaxNo(){
        return sportRepository.findMaxNo();
    }

    public List<Sport> findByNo(int no){
        return sportRepository.findByNo(no);
    }

    public List<ConvenientCluster> findClusters(){

        return sportRepository.findClusters();
    }

    public ConvenientCluster findClusterByNo(int no){
        return sportRepository.findClusterByNo(no);
    }
}