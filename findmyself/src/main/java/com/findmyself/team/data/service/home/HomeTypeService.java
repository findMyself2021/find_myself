package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.HomeType;
import com.findmyself.team.data.domain.home.OfficetelCharter;
import com.findmyself.team.data.repository.home.HomeTypeRepository;
import com.findmyself.team.data.repository.home.OfficetelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeTypeService {

    private final HomeTypeRepository homeTypeRepository;

    public HomeType findOne(Long h_code){
        return homeTypeRepository.findOne(h_code);
    }
}
