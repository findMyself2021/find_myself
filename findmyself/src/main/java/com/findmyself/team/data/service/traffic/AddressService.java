package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.TrafficAddress;
import com.findmyself.team.data.repository.traffic.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public TrafficAddress findOne(float latitude) {
        return addressRepository.findOne(latitude);
    }

    public List<TrafficAddress> findAll() {
        return addressRepository.findAll();
    }
}
