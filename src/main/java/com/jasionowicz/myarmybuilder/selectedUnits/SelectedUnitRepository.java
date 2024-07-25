package com.jasionowicz.myarmybuilder.selectedUnits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelectedUnitRepository extends JpaRepository<SelectedUnit, Integer> {

    List<SelectedUnit> findAll();


}
