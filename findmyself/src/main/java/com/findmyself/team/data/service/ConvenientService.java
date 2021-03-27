package com.findmyself.team.data.service;

import com.findmyself.team.ConvenientTmp;
import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.repository.ConvenientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConvenientService {

    private final ConvenientRepository convenientRepository;

    public Convenient findOne(long h_code){
        return convenientRepository.findOne(h_code);
    }

    public List<Convenient> findAll(){
        return  convenientRepository.findAll();
    }

    public int findMax(String kind){return convenientRepository.findMax(kind);}

    public HashSet<Long> analysis(ConvenientTmp convenient){
        int joy_std = Math.round(findMax("joy") / 100);
        int life_std = Math.round(findMax("life"));
        int shop_std = Math.round(findMax("shop"));
        int sport_std = Math.round(findMax("sport"));
        int food_std = Math.round(findMax("food"));
        int edu_std = Math.round(findMax("edu"));

        HashSet<Long> codeList = new HashSet<>();

        List<Convenient> convenientList = this.findAll();
        for(int i=0; i<convenientList.size(); i++){
            if(convenientList.get(i).getJoy() <= joy_std*convenient.getJoy()
                    && convenientList.get(i).getLife() >= life_std*convenient.getLife()
                    && convenientList.get(i).getShop() >= shop_std*convenient.getShop()
                    && convenientList.get(i).getSport() >= sport_std*convenient.getSport()
                    && food_std*convenientList.get(i).getFood() >= food_std*convenient.getFood()
                    && convenientList.get(i).getEdu() >= edu_std*convenient.getEdu()){
                codeList.add(convenientList.get(i).getH_code());
            }
        }

        return codeList;
    }
}
