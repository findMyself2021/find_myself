package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.*;
import com.findmyself.team.data.repository.home.OfficetelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OfficetelService {

    private final OfficetelRepository officetelRepository;

    public OfficetelCharter findCharterOne(Long h_code){
        return officetelRepository.findCharterOne(h_code);
    }

    public OfficetelMonthly findMonthlyOne(Long h_code){
        return officetelRepository.findMonthlyOne(h_code);
    }

    public int findCharterMaxNo(){
        return officetelRepository.findCharterMaxNo();
    }

    public int findMonthlyMaxNo(){
        return officetelRepository.findMonthlyMaxNo();
    }

    public List<OfficetelCharter> findChartersByNo(int no){
        return officetelRepository.findChartersByNo(no);
    }

    public List<OfficetelMonthly> findMonthlyByNo(int no){
        return officetelRepository.findMonthlysByNo(no);
    }

    public List<HomeCluster> findCharterClusters(){
        return officetelRepository.findCharterClusters();
    }

    public List<HomeCluster> findMonthlyClusters(){
        return officetelRepository.findMonthlyClusters();
    }

    public HomeCluster findCharterClusterByNo(int no){
        return officetelRepository.findCharterClusterByNo(no);
    }

    public HomeCluster findMonthlyClusterByNo(int no){
        return officetelRepository.findMonthlyClusterByNo(no);
    }
}
