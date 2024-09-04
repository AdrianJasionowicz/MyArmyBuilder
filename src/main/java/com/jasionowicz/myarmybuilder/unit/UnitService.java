package com.jasionowicz.myarmybuilder.unit;

import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeDTO;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UpgradeRepository upgradeRepository;


    public void deleteById(Integer id) {
        unitRepository.deleteById(id);
    }



    public List<Unit> getUnitsByNation(String nation) {
        return unitRepository.findByNation(nation);
    }
}
