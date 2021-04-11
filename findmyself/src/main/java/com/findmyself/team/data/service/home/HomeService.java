package com.findmyself.team.data.service.home;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.home.HomeApart;
import com.findmyself.team.data.domain.home.HomeDandok;
import com.findmyself.team.data.domain.home.HomeDasede;
import com.findmyself.team.data.domain.home.HomeOfficetel;
import com.findmyself.team.data.repository.home.ApartRepository;
import com.findmyself.team.data.repository.home.DandokRepository;
import com.findmyself.team.data.repository.home.DasedeRepository;
import com.findmyself.team.data.repository.home.OfficetelRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    @Getter
    private static int input_max = 20000;

    private final ApartRepository apartRepository;
    private final DandokRepository dandokRepository;
    private final DasedeRepository dasedeRepository;
    private final OfficetelRepository officetelRepository;

    //전세 - 보증금 평균
    public int findDepositAvgByCharter(Long code){
        int sum = 0;
        int cnt = 0;

        if(apartRepository.findOne(code).getCharter_deposit() == 0){
            sum+=0;
        }else{
            sum+=apartRepository.findOne(code).getCharter_deposit();
            cnt++;
        }

        if(dandokRepository.findOne(code).getCharter_deposit() == 0){
            sum+=0;
        }else{
            sum+=dandokRepository.findOne(code).getCharter_deposit();
            cnt++;
        }

        if(dasedeRepository.findOne(code).getCharter_deposit() == 0){
            sum+=0;
        }else{
            sum+=dasedeRepository.findOne(code).getCharter_deposit();
            cnt++;
        }

        if(officetelRepository.findOne(code).getCharter_deposit() == 0){
            sum+=0;
        }else{
            sum+=officetelRepository.findOne(code).getCharter_deposit();
            cnt++;
        }

        System.out.println("행정동 "+code+"에는 "+cnt+"가지 유형 거주 데이터 존재");
        if(cnt==0){
            return 0;
        }else{
            return Math.round(sum/cnt);
        }
    }

    //월세 - 보증금 평균
    public int findDepositAvgByMonthly(Long code){
        int sum = 0;
        int cnt = 0;

        if(apartRepository.findOne(code).getMonthly_deposit() == 0){
            sum+=0;
        }else{
            sum+=apartRepository.findOne(code).getMonthly_deposit();
            cnt++;
        }

        if(dandokRepository.findOne(code).getMonthly_deposit() == 0){
            sum+=0;
        }else{
            sum+=dandokRepository.findOne(code).getMonthly_deposit();
            cnt++;
        }

        if(dasedeRepository.findOne(code).getMonthly_deposit() == 0){
            sum+=0;
        }else{
            sum+=dasedeRepository.findOne(code).getMonthly_deposit();
            cnt++;
        }

        if(officetelRepository.findOne(code).getMonthly_deposit() == 0){
            sum+=0;
        }else{
            sum+=officetelRepository.findOne(code).getMonthly_deposit();
            cnt++;
        }

        System.out.println("행정동 "+code+"에는 "+cnt+"가지 유형 거주 데이터 존재");
        if(cnt==0){
            return 0;
        }else{
            return Math.round(sum/cnt);
        }
    }

    //월세 - 월세 평균
    public int findMonthlyAvgByMonthly(Long code){
        int sum = 0;
        int cnt = 0;

        if(apartRepository.findOne(code).getMonthly_monthly() == 0){
            sum+=0;
        }else{
            sum+=apartRepository.findOne(code).getMonthly_monthly();
            cnt++;
        }

        if(dandokRepository.findOne(code).getMonthly_monthly() == 0){
            sum+=0;
        }else{
            sum+=dandokRepository.findOne(code).getMonthly_monthly();
            cnt++;
        }

        if(dasedeRepository.findOne(code).getMonthly_monthly() == 0){
            sum+=0;
        }else{
            sum+=dasedeRepository.findOne(code).getMonthly_monthly();
            cnt++;
        }

        if(officetelRepository.findOne(code).getMonthly_monthly() == 0){
            sum+=0;
        }else{
            sum+=officetelRepository.findOne(code).getMonthly_monthly();
            cnt++;
        }

        System.out.println("행정동 "+code+"에는 "+cnt+"가지 유형 거주 데이터 존재");
        if(cnt==0){
            return 0;
        }else{
            return Math.round(sum/cnt);
        }
    }

    // 예산 필터링 분석
    public HashSet<Long> analysis(String home_type, int deposit, int monthly){
        HashSet<Long> codeList = new HashSet<>();

        if(home_type.equals("charter")) { //전세 선택한 경우
            System.out.println("전세 선택함");
            codeList = findCharterList(deposit);
        }else{ //월세 선택한 경우
            System.out.println("월세 선택함");
            codeList = findMonthlyList(deposit,monthly);
        }

        return codeList;
    }

    // 전세 예산 분석
    public HashSet<Long> findCharterList(int deposit){

        HashSet<Long> codeList = new HashSet<>(); //중복 허용하지 않는 Set

        List<HomeApart> apartList = apartRepository.findAll();
        List<HomeDandok> dandokList = dandokRepository.findAll();
        List<HomeDasede> dasedeList = dasedeRepository.findAll();
        List<HomeOfficetel> officetelList =  officetelRepository.findAll();

        for(int i=0; i<apartList.size(); i++){
            if(apartList.get(i).getCharter_deposit() <= deposit && apartList.get(i).getCharter_deposit()!=0){
                codeList.add(apartList.get(i).getH_code());
            }
        }

        for(int i=0; i<dandokList.size(); i++){
            if(dandokList.get(i).getCharter_deposit() <= deposit && dandokList.get(i).getCharter_deposit()!=0){
                codeList.add(dandokList.get(i).getH_code());
            }
        }

        for(int i=0; i<dasedeList.size(); i++){
            if(dasedeList.get(i).getCharter_deposit() <= deposit && dasedeList.get(i).getCharter_deposit()!=0){
                codeList.add(dasedeList.get(i).getH_code());
            }
        }

        for(int i=0; i<officetelList.size(); i++){
            if(officetelList.get(i).getCharter_deposit() <= deposit && officetelList.get(i).getCharter_deposit()!=0){
                codeList.add(officetelList.get(i).getH_code());
            }
        }
        return codeList;
    }

    // 월세 예산 분석
    public HashSet<Long> findMonthlyList(int deposit, int monthly){

        HashSet<Long> codeList = new HashSet<>(); //중복 허용하지 않는 Set

        List<HomeApart> apartList = apartRepository.findAll();
        List<HomeDandok> dandokList = dandokRepository.findAll();
        List<HomeDasede> dasedeList = dasedeRepository.findAll();
        List<HomeOfficetel> officetelList =  officetelRepository.findAll();

        for(int i=0; i<apartList.size(); i++){
            if(apartList.get(i).getMonthly_deposit() <= deposit && apartList.get(i).getMonthly_monthly() <= monthly
                && apartList.get(i).getMonthly_deposit()!=0){
                codeList.add(apartList.get(i).getH_code());
            }
        }

        for(int i=0; i<dandokList.size(); i++){
            if(dandokList.get(i).getMonthly_deposit() <= deposit && dandokList.get(i).getMonthly_monthly() <= monthly
                    && dandokList.get(i).getMonthly_deposit()!=0){
                codeList.add(dandokList.get(i).getH_code());
            }
        }

        for(int i=0; i<dasedeList.size(); i++){
            if(dasedeList.get(i).getMonthly_deposit() <= deposit && dasedeList.get(i).getMonthly_monthly() <= monthly
                    && dasedeList.get(i).getMonthly_deposit()!=0){
                codeList.add(dasedeList.get(i).getH_code());
            }
        }

        for(int i=0; i<officetelList.size(); i++){
            if(officetelList.get(i).getMonthly_deposit() <= deposit && officetelList.get(i).getMonthly_monthly() <= monthly
                    && officetelList.get(i).getMonthly_deposit()!=0){
                codeList.add(officetelList.get(i).getH_code());
            }
        }

        /*Iterator<Long> it = codeList.iterator(); // Iterator(반복자) 생성
        System.out.println("=====행정동 코드=====");

        while (it.hasNext()) { // hasNext() : 데이터가 있으면 true 없으면 false
            System.out.println(it.next()); // next() : 다음 데이터 리턴
        }*/

        return codeList;
    }

}
