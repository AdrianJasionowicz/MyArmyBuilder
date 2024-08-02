package com.jasionowicz.myarmybuilder.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    @Query("select u from Unit u join fetch u.name")
    List<Unit> findAllWithNames();

    List<Unit> findByNation(String nation);
}
