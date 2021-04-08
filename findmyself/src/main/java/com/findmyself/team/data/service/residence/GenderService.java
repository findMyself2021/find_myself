package com.findmyself.team.data.service.residence;

import com.findmyself.team.data.domain.residence.ResidenceGender;
import com.findmyself.team.data.repository.residence.GenderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;

    public ResidenceGender findOne(long h_code){
        return genderRepository.findOne(h_code);
    }

    public List<ResidenceGender> findAll(){
        return genderRepository.findAll();
    }

    public double findMax(){
        return genderRepository.findMax();
    }

    public double findMin(){
        return genderRepository.findMin();
    }

    public HashSet<Long> analysis(int sex_ratio){


        int mid = getMidValue();
        int std_value = getStdVaule(sex_ratio); // 데이터에 맞춰 표준화한 필터값
        int real_value; // 실제 성비 데이터 값

        HashSet<Long> codeList = new HashSet<>();

        List<ResidenceGender> genderList = this.findAll();
        for(int i=0; i<genderList.size(); i++){
            real_value = (int)genderList.get(i).getSex_ratio();
            if(findPrefer(std_value,mid).equals("w")){ //여초 선호
                if(real_value <= std_value){
                    codeList.add(genderList.get(i).getH_code());
                }
            }else if(findPrefer(std_value,mid).equals("m")){ //남초 선호
                if(real_value >= std_value){
                    codeList.add(genderList.get(i).getH_code());
                }
            }else{ //1:1 성비
                if(real_value >= std_value-20 && real_value <= std_value+20){
                    codeList.add(genderList.get(i).getH_code());
                }
            }
        }

        return codeList;
    }

    public int getMidValue(){

        int min = (int)findMin();
        int max = (int)findMax();
        int mid = min + Math.round((max-min)/2);

        return mid;
    }

    public int getStdVaule(int sex_ratio){

        int min = (int)findMin();
        int max = (int)findMax();
        int std = Math.round((max-min)/100);
        int std_value = min+std*sex_ratio;

        return std_value;
    }

    public String findPrefer(int std_value, int mid){
        if(std_value < mid - 20){   //여초 선호
            return "w";
        }else if(std_value > mid + 20){ //남초 선호
            return "m";
        }
        else {  // 비슷한 비율 선호
            return "none";
        }
    }
}
