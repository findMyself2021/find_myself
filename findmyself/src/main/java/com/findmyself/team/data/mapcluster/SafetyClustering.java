package com.findmyself.team.data.mapcluster;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.traffic.Traffic;
import com.findmyself.team.data.service.SafetyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SafetyClustering {
    @Autowired
    SafetyService safetyService;

    public List<SafetyLite> clusterSafety(){
        List<SafetyLite> list = new ArrayList<>();
        List<Safety> safetyList = safetyService.findAll();

        for(int i=0;i<safetyList.size();i++){
            Long h_code = safetyList.get(i).getH_code();
            int no = safetyList.get(i).getNo();

            SafetyLite safetyLite = new SafetyLite(h_code,no);
            list.add(safetyLite);
        }

        return list;
    }
}
