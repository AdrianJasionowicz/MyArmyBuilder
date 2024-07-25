    package com.jasionowicz.myarmybuilder.selectedUpgrades;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface SelectedUpgradesRepository extends JpaRepository<SelectedUpgrades, Integer> {


        List<SelectedUpgrades> findAllBySelectedUnitId(Integer unitId);
    }
