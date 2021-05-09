package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.ApartCharter;
import com.findmyself.team.data.domain.home.ApartMonthly;
import com.findmyself.team.data.domain.home.HomeCluster;
import com.findmyself.team.data.repository.home.ApartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApartService {

    private final ApartRepository apartRepository;

    public ApartCharter findCharterOne(Long h_code){
        return apartRepository.findCharterOne(h_code);
    }

    public ApartMonthly findMonthlyOne(Long h_code){
        return apartRepository.findMonthlyOne(h_code);
    }

    public int findCharterMaxNo(){
        return apartRepository.findCharterMaxNo();
    }

    public int findMonthlyMaxNo(){
        return apartRepository.findMonthlyMaxNo();
    }

    public List<ApartCharter> findChartersByNo(int no){
        return apartRepository.findChartersByNo(no);
    }

    public List<ApartMonthly> findMonthlyByNo(int no){
        return apartRepository.findMonthlysByNo(no);
    }

    public List<HomeCluster> findCharterClusters(){
        return apartRepository.findCharterClusters();
    }

    public List<HomeCluster> findMonthlyClusters(){
        return apartRepository.findMonthlyClusters();
    }

    public HomeCluster findCharterClusterByNo(int no){
        return apartRepository.findCharterClusterByNo(no);
    }

    public HomeCluster findMonthlyClusterByNo(int no){
        return apartRepository.findMonthlyClusterByNo(no);
    }
}
