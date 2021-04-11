package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.HomeOfficetel;
import com.findmyself.team.data.repository.home.OfficetelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OfficetelService {

    private final OfficetelRepository officetelRepository;

    public HomeOfficetel findOne(Long h_code){
        return officetelRepository.findOne(h_code);
    }

    public List<HomeOfficetel> findAll(){
        return officetelRepository.findAll();
    }
}
