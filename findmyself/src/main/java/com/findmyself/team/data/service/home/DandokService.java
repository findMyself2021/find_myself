package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.*;
import com.findmyself.team.data.repository.home.DandokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DandokService {

    private final DandokRepository dandokRepository;

    public DandokCharter findCharterOne(Long h_code){
        return dandokRepository.findCharterOne(h_code);
    }

    public DandokMonthly findMonthlyOne(Long h_code){
        return dandokRepository.findMonthlyOne(h_code);
    }

    public int findCharterMaxNo(){
        return dandokRepository.findCharterMaxNo();
    }

    public int findMonthlyMaxNo(){
        return dandokRepository.findMonthlyMaxNo();
    }

    public List<DandokCharter> findChartersByNo(int no){
        return dandokRepository.findChartersByNo(no);
    }

    public List<DandokMonthly> findMonthlyByNo(int no){
        return dandokRepository.findMonthlysByNo(no);
    }

    public List<HomeCluster> findCharterClusters(){
        return dandokRepository.findCharterClusters();
    }

    public List<HomeCluster> findMonthlyClusters(){
        return dandokRepository.findMonthlyClusters();
    }

    public HomeCluster findCharterClusterByNo(int no){
        return dandokRepository.findCharterClusterByNo(no);
    }

    public HomeCluster findMonthlyClusterByNo(int no){
        return dandokRepository.findMonthlyClusterByNo(no);
    }
}
