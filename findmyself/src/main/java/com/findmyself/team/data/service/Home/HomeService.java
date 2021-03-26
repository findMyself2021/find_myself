package com.findmyself.team.data.service.Home;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.*;
import com.findmyself.team.data.repository.Home.ApartRepository;
import com.findmyself.team.data.repository.Home.DandokRepository;
import com.findmyself.team.data.repository.Home.DasedeRepository;
import com.findmyself.team.data.repository.Home.OfficetelRepository;
import com.findmyself.team.data.service.GudongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final ApartRepository apartRepository;
    private final DandokRepository dandokRepository;
    private final DasedeRepository dasedeRepository;
    private final OfficetelRepository officetelRepository;

    public void analysis(Requirements rq){
        if(rq.getHome_type().equals("charter")) { //전세 선택한 경우
            System.out.println("전세 선택함");
            findCharterList(rq.getDeposit());
        }else{ //월세 선택한 경우
            System.out.println("월세 선택함");
            findMonthlyList(rq.getDeposit(),rq.getMonthly());
        }
    }

    public void findCharterList(int deposit){

        HashSet<Long> codeList = new HashSet<>(); //중복 허용하지 않는 Set

        List<HomeApart> apartList = apartRepository.findCharters();
        List<HomeDandok> dandokList = dandokRepository.findCharters();
        List<HomeDasede> dasedeList = dasedeRepository.findCharters();
        List<HomeOfficetel> officetelList =  officetelRepository.findCharters();

        for(int i=0; i<apartList.size(); i++){
            if(apartList.get(i).getAvg_deposit() <= deposit){
                codeList.add(apartList.get(i).getH_code());
            }
        }

        for(int i=0; i<dandokList.size(); i++){
            if(dandokList.get(i).getAvg_deposit() <= deposit){
                codeList.add(dandokList.get(i).getH_code());
            }
        }

        for(int i=0; i<dasedeList.size(); i++){
            if(dasedeList.get(i).getAvg_deposit() <= deposit){
                codeList.add(dasedeList.get(i).getH_code());
            }
        }

        for(int i=0; i<officetelList.size(); i++){
            if(officetelList.get(i).getAvg_deposit() <= deposit){
                codeList.add(officetelList.get(i).getH_code());
            }
        }

        Iterator<Long> it = codeList.iterator(); // Iterator(반복자) 생성
        System.out.println("=====행정동 코드=====");

        while (it.hasNext()) { // hasNext() : 데이터가 있으면 true 없으면 false
            System.out.println(it.next()); // next() : 다음 데이터 리턴
        }
    }

    public void findMonthlyList(int deposit, int monthly){

        HashSet<Long> codeList = new HashSet<>(); //중복 허용하지 않는 Set

        List<HomeApart> apartList = apartRepository.findMonthly();
        List<HomeDandok> dandokList = dandokRepository.findMonthly();
        List<HomeDasede> dasedeList = dasedeRepository.findMonthly();
        List<HomeOfficetel> officetelList =  officetelRepository.findMonthly();

        for(int i=0; i<apartList.size(); i++){
            if(apartList.get(i).getAvg_deposit() <= deposit && apartList.get(i).getAvg_monthly() <= monthly){
                codeList.add(apartList.get(i).getH_code());
            }
        }

        for(int i=0; i<dandokList.size(); i++){
            if(dandokList.get(i).getAvg_deposit() <= deposit && dandokList.get(i).getAvg_monthly() <= monthly){
                codeList.add(dandokList.get(i).getH_code());
            }
        }

        for(int i=0; i<dasedeList.size(); i++){
            if(dasedeList.get(i).getAvg_deposit() <= deposit && dasedeList.get(i).getAvg_monthly() <= monthly){
                codeList.add(dasedeList.get(i).getH_code());
            }
        }

        for(int i=0; i<officetelList.size(); i++){
            if(officetelList.get(i).getAvg_deposit() <= deposit && officetelList.get(i).getAvg_monthly() <= monthly){
                codeList.add(officetelList.get(i).getH_code());
            }
        }

        Iterator<Long> it = codeList.iterator(); // Iterator(반복자) 생성
        System.out.println("=====행정동 코드=====");

        while (it.hasNext()) { // hasNext() : 데이터가 있으면 true 없으면 false
            System.out.println(it.next()); // next() : 다음 데이터 리턴
        }
    }
}
