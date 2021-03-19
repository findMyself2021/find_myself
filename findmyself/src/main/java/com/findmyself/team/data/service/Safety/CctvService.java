package com.findmyself.team.data.service.Safety;

import com.findmyself.team.data.domain.SafetyCctv;
import com.findmyself.team.data.repository.Safety.CctvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CctvService {

    private final CctvRepository cctvRepository;

    public SafetyCctv findOne(String gu) {
        return cctvRepository.findOne(gu);
    }

    public List<SafetyCctv> findAll() {
        return cctvRepository.findAll();
    }
}
