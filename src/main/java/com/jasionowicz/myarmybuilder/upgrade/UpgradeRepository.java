package com.jasionowicz.myarmybuilder.upgrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UpgradeRepository extends JpaRepository<Upgrade, Integer> {

    List<Upgrade> findAllByUnitId(Integer id);
}
