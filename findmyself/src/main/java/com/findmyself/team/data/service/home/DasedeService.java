package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.*;
import com.findmyself.team.data.repository.home.DasedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DasedeService {

    private final DasedeRepository dasedeRepository;

    public DasedeCharter findCharterOne(Long h_code){
        return dasedeRepository.findCharterOne(h_code);
    }

    public DasedeMonthly findMonthlyOne(Long h_code){
        return dasedeRepository.findMonthlyOne(h_code);
    }

    public int findCharterMaxNo(){
        return dasedeRepository.findCharterMaxNo();
    }

    public int findMonthlyMaxNo(){
        return dasedeRepository.findMonthlyMaxNo();
    }

    public List<DasedeCharter> findChartersByNo(int no){
        return dasedeRepository.findChartersByNo(no);
    }

    public List<DasedeMonthly> findMonthlyByNo(int no){
        return dasedeRepository.findMonthlysByNo(no);
    }

    public List<HomeCluster> findCharterClusters(){
        return dasedeRepository.findCharterClusters();
    }

    public List<HomeCluster> findMonthlyClusters(){
        return dasedeRepository.findMonthlyClusters();
    }

    public HomeCluster findCharterClusterByNo(int no){
        return dasedeRepository.findCharterClusterByNo(no);
    }

    public HomeCluster findMonthlyClusterByNo(int no){
        return dasedeRepository.findMonthlyClusterByNo(no);
    }
}
