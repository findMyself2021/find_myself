package com.findmyself.team.data.service.convenient;

import com.findmyself.team.data.domain.SafetyCluster;
import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Joy;
import com.findmyself.team.data.domain.convenient.Shop;
import com.findmyself.team.data.repository.convenient.JoyRepository;
import com.findmyself.team.data.repository.convenient.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop findOne(Long h_code) {
        return shopRepository.findOne(h_code);
    }

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public int findMax(){
        return shopRepository.findMax();
    }

    public int findMin(){
        return shopRepository.findMin();
    }

    //군집 갯수
    public int findMaxNo(){
        return shopRepository.findMaxNo();
    }

    public List<Shop> findByNo(int no){
        return shopRepository.findByNo(no);
    }

    public List<ConvenientCluster> findClusters(){

        return shopRepository.findClusters();
    }

    public ConvenientCluster findClusterByNo(int no){
        return shopRepository.findClusterByNo(no);
    }
}