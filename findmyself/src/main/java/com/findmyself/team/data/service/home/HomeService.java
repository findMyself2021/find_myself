package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.HomeCluster;
import com.findmyself.team.data.repository.home.ApartRepository;
import com.findmyself.team.data.repository.home.DandokRepository;
import com.findmyself.team.data.repository.home.DasedeRepository;
import com.findmyself.team.data.repository.home.OfficetelRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final ApartRepository apartRepository;
    private final DandokRepository dandokRepository;
    private final DasedeRepository dasedeRepository;
    private final OfficetelRepository officetelRepository;

    //전세 - 보증금 평균
    public int findDepositAvgByCharter(Long code){

        int sum = 0;

        sum += apartRepository.findCharterOne(code).getDeposit();
        sum += dandokRepository.findCharterOne(code).getDeposit();
        sum += dasedeRepository.findCharterOne(code).getDeposit();
        sum += officetelRepository.findCharterOne(code).getDeposit();

        return Math.round(sum/4);
    }

    //월세 - 보증금 평균
    public int findDepositAvgByMonthly(Long code){

        int sum = 0;

        sum += apartRepository.findMonthlyOne(code).getDeposit();
        sum += dandokRepository.findMonthlyOne(code).getDeposit();
        sum += dasedeRepository.findMonthlyOne(code).getDeposit();
        sum += officetelRepository.findMonthlyOne(code).getDeposit();

        return Math.round(sum/4);
    }

    //월세 - 월세 평균
    public int findMonthlyAvgByMonthly(Long code){

        int sum = 0;

        sum += apartRepository.findMonthlyOne(code).getMonthly();
        sum += dandokRepository.findMonthlyOne(code).getMonthly();
        sum += dasedeRepository.findMonthlyOne(code).getMonthly();
        sum += officetelRepository.findMonthlyOne(code).getMonthly();

        return Math.round(sum/4);
    }

    //설정값을 포함하는 군집번호 찾기
    public List<Integer> findCharterClusterNo(String type, int deposit){

        List<HomeCluster> clusters = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        if(type.equals("apart")){
            clusters = apartRepository.findCharterClusters();
        }else if(type.equals("dandok")){
            clusters = dandokRepository.findCharterClusters();
        }else if(type.equals("dasede")){
            clusters = dasedeRepository.findCharterClusters();
        }else{
            clusters = officetelRepository.findCharterClusters();
        }

        for(HomeCluster cluster: clusters){
            if(cluster.getDeposit_min() <= deposit && cluster.getDeposit_max() >= deposit){
                numbers.add(cluster.getNo());
            }
        }
        return numbers;
    }
    public List<Integer> findMonthlyClusterNo(String type, int deposit, int monthly){

        List<HomeCluster> clusters;
        List<Integer> numbers = new ArrayList<>();

        if(type.equals("apart")){
            clusters = apartRepository.findMonthlyClusters();
        }else if(type.equals("dandok")){
            clusters = dandokRepository.findMonthlyClusters();
        }else if(type.equals("dasede")){
            clusters = dasedeRepository.findMonthlyClusters();
        }else{
            clusters = officetelRepository.findMonthlyClusters();
        }
        for(HomeCluster cluster: clusters){
            if(cluster.getDeposit_min() <= deposit && cluster.getDeposit_max() >= deposit &&
                    cluster.getMonthly_min() <= monthly && cluster.getMonthly_max() >= monthly){
                numbers.add(cluster.getNo());
            }
        }
        return numbers;
    }

    // 예산 필터링 분석
    // 설정 예산값을 포함하는 범위의 클러스터 내 행정동 모두 추가
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

        HashSet<Long> codeList = new HashSet<>();   //설정값을 충족하는 행정동 코드 셋(중복 허용하지 않는 Set)

        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호
        List<Long> tmpCodes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        numbers = findCharterClusterNo("apart",deposit);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = apartRepository.findCharterClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        numbers = findCharterClusterNo("dandok",deposit);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = dandokRepository.findCharterClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        numbers = findCharterClusterNo("dasede",deposit);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = dasedeRepository.findCharterClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        numbers = findCharterClusterNo("officetel",deposit);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = officetelRepository.findCharterClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        return codeList;
    }

    // 월세 예산 분석
    public HashSet<Long> findMonthlyList(int deposit, int monthly){

        HashSet<Long> codeList = new HashSet<>();   //설정값을 충족하는 행정동 코드 셋(중복 허용하지 않는 Set)

        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호
        List<Long> tmpCodes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        numbers = findMonthlyClusterNo("apart", deposit, monthly);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = apartRepository.findMonthlyClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        numbers = findMonthlyClusterNo("dandok", deposit, monthly);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = dandokRepository.findMonthlyClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        numbers = findMonthlyClusterNo("dasede", deposit, monthly);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = dasedeRepository.findMonthlyClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        numbers = findMonthlyClusterNo("officetel", deposit, monthly);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = officetelRepository.findMonthlyClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
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
