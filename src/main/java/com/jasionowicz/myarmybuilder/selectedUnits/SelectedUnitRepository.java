package com.jasionowicz.myarmybuilder.selectedUnits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelectedUnitRepository extends JpaRepository<SelectedUnit, Integer> {

    List<SelectedUnit> findAll();

    @Query ("select  u from SelectedUnit u where u.unit.id=:id")
    List<SelectedUnit> findAllByUnitId(Integer id);

}
