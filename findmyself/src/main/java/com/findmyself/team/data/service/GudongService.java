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

    public Optional<Gudong> findOne(long h_code) {
        return gudongRepository.findById(h_code);
    }

    public Iterable<Gudong> findAll(){
        return  gudongRepository.findAll();
    }
}
