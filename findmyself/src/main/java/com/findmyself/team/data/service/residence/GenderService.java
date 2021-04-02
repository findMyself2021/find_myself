package com.findmyself.team.data.service.residence;

import com.findmyself.team.data.domain.residence.ResidenceGender;
import com.findmyself.team.data.repository.residence.GenderRepository;
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
        int min = (int)findMin();
        int max = (int)findMax();
        int mid = min + Math.round((max-min)/2);
        int std = Math.round((max-min)/100);
        int std_value, real_value;

        HashSet<Long> codeList = new HashSet<>();

        List<ResidenceGender> genderList = this.findAll();
        for(int i=0; i<genderList.size(); i++){
            std_value = min+std*sex_ratio; // 데이터에 맞춰 표준화한 필터값
            real_value = (int)genderList.get(i).getSex_ratio(); // 실제 성비 데이터 값
            if(std_value<mid){ //여초 선호
                if(real_value <= std_value){
                    codeList.add(genderList.get(i).getH_code());
                }
            }else if(std_value>mid){ //남초 선호
                if(real_value >= std_value){
                    codeList.add(genderList.get(i).getH_code());
                }
            }else{ //1:1 성비
                if(real_value >= std_value-30 && real_value <= std_value+30){
                    codeList.add(genderList.get(i).getH_code());
                }
            }
        }

        return codeList;
    }
}
