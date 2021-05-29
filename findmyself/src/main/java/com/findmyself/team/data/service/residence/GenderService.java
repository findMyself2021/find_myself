package com.findmyself.team.data.service.residence;

import com.findmyself.team.data.domain.home.HomeCluster;
import com.findmyself.team.data.domain.residence.Gender;
import com.findmyself.team.data.domain.residence.GenderCluster;
import com.findmyself.team.data.repository.residence.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;

    public Gender findOne(long h_code){
        return genderRepository.findOne(h_code);
    }

    public List<Gender> findAll(){
        return genderRepository.findAll();
    }

    public double findMax(){
        return genderRepository.findMax();
    }

    public double findMin(){
        return genderRepository.findMin();
    }

    public int findMaxNo(){
        return genderRepository.findMaxNo();
    }

    public List<Gender> findByNo(int no){
        return genderRepository.findByNo(no);
    }

    public List<GenderCluster> findClusters(){
        return genderRepository.findClusters();
    }

    public GenderCluster findClusterByNo(int no){
        return genderRepository.findClusterByNo(no);
    }

    //설정값을 포함하는 군집번호 찾기
    public List<Integer> findClusterNo(int ratio){

        int mid_value = getMidValue();
        int std_value = getStdVaule(ratio); // 데이터에 맞춰 표준화한 필터값

        List<GenderCluster> clusters = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        clusters = genderRepository.findClusters();

        for(GenderCluster cluster: clusters){
            if(findPrefer(std_value).equals("w")){ //여초 선호
                if(std_value <= cluster.getRatio_max()){
                    numbers.add(cluster.getNo());
                }
            }else if(findPrefer(std_value).equals("m")){ //남초 선호
                if(mid_value <= cluster.getRatio_max()){
                    numbers.add(cluster.getNo());
                }
            }else{ //1:1 성비
                if(cluster.getRatio_min() >= mid_value-40 && cluster.getRatio_max() <= mid_value+40){
                    numbers.add(cluster.getNo());
                }
            }
        }
        return numbers;

    }

    public List<Long> analysis(int ratio){

        List<Long> codeList = new ArrayList<>();

        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호
        List<Long> tmpCodes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        numbers = findClusterNo(ratio);
        System.out.println("젠더 클러스터: "+numbers);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = genderRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        return codeList;
    }

    public int getMidValue(){

        int min = (int)findMin();
        int max = (int)findMax();
        int mid = min + Math.round((float)(max-min)/2);

        return mid;
    }

    public int getStdVaule(int ratio){

        int min = (int)findMin();
        int max = (int)findMax();
        int std = Math.round((float)(max-min)/100);
        int std_value = min+std*ratio;

        return std_value;
    }

    public String findPrefer(int std_value){
        if(std_value < 95){   //여초 선호
            return "w";
        }else if(std_value > 105){ //남초 선호
            return "m";
        }
        else {  // 비슷한 비율 선호
            return "none";
        }
    }
}
