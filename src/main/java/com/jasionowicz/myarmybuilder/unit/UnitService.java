package com.jasionowicz.myarmybuilder.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    public void deleteById(Integer id) {
        unitRepository.deleteById(id);
    }

}
