package com.findmyself.team.data.service.convenient;

import com.findmyself.team.ConvenientTmp;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.convenient.ConvenientCluster;
import com.findmyself.team.data.domain.convenient.Edu;
import com.findmyself.team.data.repository.convenient.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConvenientService {

    private final EduRepository eduRepository;
    private final FoodRepository foodRepository;
    private final JoyRepository joyRepository;
    private final SportRepository sportRepository;
    private final ShopRepository shopRepository;
    private final LifeRepository lifeRepository;

    //설정값을 포함하는 군집번호 찾기
    public List<Integer> findClusterNo(String type, int value){
        int std_value = getStdVaule(type, value);

        List<ConvenientCluster> clusters = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        if(type.equals("edu")){
            clusters = eduRepository.findClusters();

            for(ConvenientCluster cluster: clusters){
                if(cluster.getMin()>=std_value){
                    numbers.add(cluster.getNo());
                }
            }
        }else if(type.equals("food")){
            clusters = foodRepository.findClusters();

            for(ConvenientCluster cluster: clusters){
                if(cluster.getMin()>=std_value){
                    numbers.add(cluster.getNo());
                }
            }
        }else if(type.equals("joy")){
            clusters = joyRepository.findClusters();

            for(ConvenientCluster cluster: clusters){
                if(cluster.getMin()>=std_value){
                    numbers.add(cluster.getNo());
                }
            }
        }else if(type.equals("sport")){
            clusters = sportRepository.findClusters();

            for(ConvenientCluster cluster: clusters){
                if(cluster.getMin()>=std_value){
                    numbers.add(cluster.getNo());
                }
            }
        }else if(type.equals("shop")){
            clusters = shopRepository.findClusters();

            for(ConvenientCluster cluster: clusters){
                if(cluster.getMin()>=std_value){
                    numbers.add(cluster.getNo());
                }
            }
        }else{
            clusters = lifeRepository.findClusters();

            for(ConvenientCluster cluster: clusters){
                if(cluster.getMin()>=std_value){
                    numbers.add(cluster.getNo());
                }
            }
        }

        return numbers;
    }

    public List<Long> analysis(ConvenientTmp convenient){
        List<Long> codeList = new ArrayList<>();

        //1. 여러번 중복되는 행정동만 추가하는 방식
        int cnt = 0;    //중복 되는 횟수
        List<Edu> totalList = eduRepository.findAll(); //전체 행정동 코드 얻기위해 사용
        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호
        List<Long> tmpCodes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        List<Long> eduCodes = new ArrayList<>();
        List<Long> foodCodes = new ArrayList<>();
        List<Long> joyCodes = new ArrayList<>();
        List<Long> lifeCodes = new ArrayList<>();
        List<Long> shopCodes = new ArrayList<>();
        List<Long> sportCodes = new ArrayList<>();

        numbers = findClusterNo("edu",convenient.getEdu());
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = eduRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    eduCodes.add(code);
                }
            }
        }
        numbers = findClusterNo("food",convenient.getFood());
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = foodRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    foodCodes.add(code);
                }
            }
        }
        numbers = findClusterNo("joy",convenient.getJoy());
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = joyRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    joyCodes.add(code);
                }
            }
        }
        numbers = findClusterNo("life",convenient.getLife());
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = lifeRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    lifeCodes.add(code);
                }
            }
        }
        numbers = findClusterNo("shop",convenient.getShop());
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = shopRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    shopCodes.add(code);
                }
            }
        }
        numbers = findClusterNo("sport",convenient.getSport());
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = sportRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    sportCodes.add(code);
                }
            }
        }

        for(Edu i : totalList){
            if(!eduCodes.isEmpty() && eduCodes.contains(i.getH_code())){
                cnt++;
            }
            if(!foodCodes.isEmpty() && foodCodes.contains(i.getH_code())){
                cnt++;
            }
            if(!joyCodes.isEmpty() && joyCodes.contains(i.getH_code())){
                cnt++;
            }
            if(!lifeCodes.isEmpty() && lifeCodes.contains(i.getH_code())){
                cnt++;
            }
            if(!shopCodes.isEmpty() && shopCodes.contains(i.getH_code())){
                cnt++;
            }
            if(!sportCodes.isEmpty() && sportCodes.contains(i.getH_code())){
                cnt++;
            }

            if(cnt>3){
                codeList.add(i.getH_code());
            }
        }

        return codeList;
    }

    public int getStdVaule(String type, int value){

        int min, max, std, std_value;

        if(type.equals("edu")){
            min = (int)eduRepository.findMin();
            max = (int)eduRepository.findMax();
            std = Math.round((max-min)/100);
            std_value = min+std*value;
        }else if(type.equals("food")){
            min = (int)foodRepository.findMin();
            max = (int)foodRepository.findMax();
            std = Math.round((max-min)/100);
            std_value = min+std*value;
        }else if(type.equals("joy")){
            min = (int)joyRepository.findMin();
            max = (int)joyRepository.findMax();
            std = Math.round((max-min)/100);
            std_value = min+std*value;
        }else if(type.equals("sport")){
            min = (int)sportRepository.findMin();
            max = (int)sportRepository.findMax();
            std = Math.round((max-min)/100);
            std_value = min+std*value;
        }else if(type.equals("shop")){
            min = (int)shopRepository.findMin();
            max = (int)shopRepository.findMax();
            std = Math.round((max-min)/100);
            std_value = min+std*value;
        }else{
            min = (int)lifeRepository.findMin();
            max = (int)lifeRepository.findMax();
            std = Math.round((max-min)/100);
            std_value = min+std*value;
        }

        return std_value;
    }

    public int findInterval(Requirements rq, Long code){
        int interval = 0;

        interval += Math.abs(getStdVaule("joy",rq.getJoy()) - joyRepository.findOne(code).getValue());
        interval += Math.abs(getStdVaule("shop",rq.getShop()) - shopRepository.findOne(code).getValue());
        interval += Math.abs(getStdVaule("food",rq.getFood()) - foodRepository.findOne(code).getValue());
        interval += Math.abs(getStdVaule("life",rq.getLife()) - lifeRepository.findOne(code).getValue());
        interval += Math.abs(getStdVaule("sport",rq.getSport()) - sportRepository.findOne(code).getValue());
        interval += Math.abs(getStdVaule("edu",rq.getEdu()) - eduRepository.findOne(code).getValue());

        return Math.round(interval/6);
    }
}
