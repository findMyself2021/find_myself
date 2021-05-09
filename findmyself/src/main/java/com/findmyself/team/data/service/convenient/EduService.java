package com.findmyself.team.data.service.convenient;

import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Edu;
import com.findmyself.team.data.repository.convenient.EduRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EduService {

    private final EduRepository eduRepository;

    public Edu findOne(Long h_code) {
        return eduRepository.findOne(h_code);
    }

    public List<Edu> findAll() {
        return eduRepository.findAll();
    }

    public int findMax(){
        return eduRepository.findMax();
    }

    public int findMin(){
        return eduRepository.findMin();
    }

    //군집 갯수
    public int findMaxNo(){
        return eduRepository.findMaxNo();
    }

    public List<Edu> findByNo(int no){
        return eduRepository.findByNo(no);
    }

    public List<ConvenientCluster> findClusters(){

        return eduRepository.findClusters();
    }

    public ConvenientCluster findClusterByNo(int no){
        return eduRepository.findClusterByNo(no);
    }
}
