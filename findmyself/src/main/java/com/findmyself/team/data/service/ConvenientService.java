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

    public HashSet<Long> analysis(ConvenientTmp convenient){
        HashSet<Long> codeList = new HashSet<>();

        return codeList;
    }
}
