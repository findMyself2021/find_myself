package com.findmyself.team.data.service.Residence;

import com.findmyself.team.data.domain.ResidenceGender;
import com.findmyself.team.data.repository.Residence.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
