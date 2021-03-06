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
            //System.out.println("전세 선택함");
            codeList = findCharterList(deposit);
        }else{ //월세 선택한 경우
            //System.out.println("월세 선택함");
            codeList = findMonthlyList(deposit,monthly);
        }

        return codeList;
    }

    // 전세 예산 분석
    public HashSet<Long> findCharterList(int deposit){

        HashSet<Long> codeList = new HashSet<>();   //설정값을 충족하는 행정동 코드 셋(중복 허용하지 않는 Set)

        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호

        numbers = findCharterClusterNo("apart",deposit);
        System.out.println("아파트 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: apartRepository.findCharterClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }

        numbers = findCharterClusterNo("dandok",deposit);
        System.out.println("단독 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: dandokRepository.findCharterClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }

        numbers = findCharterClusterNo("dasede",deposit);
        System.out.println("다세대 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: dasedeRepository.findCharterClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }

        numbers = findCharterClusterNo("officetel",deposit);
        System.out.println("오피스텔 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: officetelRepository.findCharterClusterByNo(no).getCodes()){
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

        numbers = findMonthlyClusterNo("apart", deposit, monthly);
        System.out.println("아파트 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: apartRepository.findMonthlyClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }

        numbers = findMonthlyClusterNo("dandok", deposit, monthly);
        System.out.println("단독 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: dandokRepository.findMonthlyClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }

        numbers = findMonthlyClusterNo("dasede", deposit, monthly);
        System.out.println("다세대 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: dasedeRepository.findMonthlyClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }

        numbers = findMonthlyClusterNo("officetel", deposit, monthly);
        System.out.println("오피스텔 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                for(Long code: officetelRepository.findMonthlyClusterByNo(no).getCodes()){
                    codeList.add(code);
                }
            }
        }
        return codeList;
    }

}
