package com.findmyself.team.data.service.convenient;

import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Food;
import com.findmyself.team.data.domain.convenient.Life;
import com.findmyself.team.data.repository.convenient.FoodRepository;
import com.findmyself.team.data.repository.convenient.LifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public Food findOne(Long h_code) {
        return foodRepository.findOne(h_code);
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public int findMax(){
        return foodRepository.findMax();
    }

    public int findMin(){
        return foodRepository.findMin();
    }

    //군집 갯수
    public int findMaxNo(){
        return foodRepository.findMaxNo();
    }

    public List<Food> findByNo(int no){
        return foodRepository.findByNo(no);
    }

    public List<ConvenientCluster> findClusters(){

        return foodRepository.findClusters();
    }

    public ConvenientCluster findClusterByNo(int no){
        return foodRepository.findClusterByNo(no);
    }
}