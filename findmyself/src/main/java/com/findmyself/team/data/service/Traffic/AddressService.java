package com.findmyself.team.data.service.Traffic;

import com.findmyself.team.data.domain.TrafficAddress;
import com.findmyself.team.data.repository.Traffic.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public TrafficAddress findOne(String num) {
        return addressRepository.findOne(num);
    }

    public List<TrafficAddress> findAll() {
        return addressRepository.findAll();
    }
}
