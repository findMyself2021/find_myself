package com.findmyself.team.data.service.convenient;

import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Edu;
import com.findmyself.team.data.domain.convenient.Joy;
import com.findmyself.team.data.repository.convenient.EduRepository;
import com.findmyself.team.data.repository.convenient.JoyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoyService {

    private final JoyRepository joyRepository;

    public Joy findOne(Long h_code) {
        return joyRepository.findOne(h_code);
    }

    public List<Joy> findAll() {
        return joyRepository.findAll();
    }

    public int findMax(){
        return joyRepository.findMax();
    }

    public int findMin(){
        return joyRepository.findMin();
    }

    //군집 갯수
    public int findMaxNo(){
        return joyRepository.findMaxNo();
    }

    public List<Joy> findByNo(int no){
        return joyRepository.findByNo(no);
    }

    public List<ConvenientCluster> findClusters(){

        return joyRepository.findClusters();
    }

    public ConvenientCluster findClusterByNo(int no){
        return joyRepository.findClusterByNo(no);
    }
}

