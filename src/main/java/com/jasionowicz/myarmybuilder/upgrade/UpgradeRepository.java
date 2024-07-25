package com.jasionowicz.myarmybuilder.upgrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UpgradeRepository extends JpaRepository<Upgrade, Integer> {


    @Query("SELECT u FROM Upgrade u WHERE u.unit.id = ?1")
    List<Upgrade> findAllByUnitUpgradesId(Integer unitId);

    List<Upgrade> findUpgradesByUnitId (Integer unitId);

    List<Upgrade> findAllByUnitId(Integer id);
}
