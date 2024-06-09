package com.jasionowicz.myarmybuilder.upgrade;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UpgradesService {

    private final UpgradeRepository upgradeRepository;

    public UpgradesService(UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
    }

    public List<Upgrade> findUpgradesByUnitId(Integer unitId) {
        return upgradeRepository.findAllByUnitUpgradesId(unitId);
    }

    public List<Upgrade> findAll() {
        return upgradeRepository.findAll();
    }

    public void addUpgrade(Upgrade upgrade) {
        upgradeRepository.save(upgrade);
    }

    public void deleteUpgradeById(Integer id) {
        upgradeRepository.deleteById(id);
    }

    public Upgrade getUpgradeById(Integer id) {
        return upgradeRepository.findById(id).orElse(null);
    }

    public void updateUpgrade(Integer id, Upgrade upgrade) {
        Upgrade existingUpgrade = upgradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Upgrade not found"));
        existingUpgrade.setName(upgrade.getName());
        existingUpgrade.setQuantity(upgrade.getQuantity());
        existingUpgrade.setPointsCost(upgrade.getPointsCost());
        existingUpgrade.setSelected(upgrade.isSelected());
        upgradeRepository.save(existingUpgrade);
    }
}
