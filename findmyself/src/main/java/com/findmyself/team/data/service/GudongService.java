package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.repository.GudongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GudongService {

    private final GudongRepository gudongRepository;

    public Gudong findOne(long h_code) {
        return gudongRepository.findOne(h_code);
    }

    public List<Gudong> findAll(){
        return  gudongRepository.findAll();
    }

    public String findNameByCode(Long h_code){
        return findOne(h_code).getH_dong();
    }
}
