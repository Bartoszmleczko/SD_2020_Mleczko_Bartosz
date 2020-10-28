package pl.mleczko.PlantExpertSystem.Repository;

import jess.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.FactorType;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;

import java.util.List;

public interface RiskFactorRepository extends JpaRepository<RiskFactor, Long> {

    public List<RiskFactor> findAllByRiskIdIsBetween(Integer start, Integer end );

    public List<RiskFactor> findAllByFactorType(FactorType type);

    public List<RiskFactor> findAllByPlantTypeAndFactorType(PlantType type, FactorType factorType);

    public RiskFactor findBySlotName(String slotName);

    public List<RiskFactor> findAllBySlotNameInAndPlantType(List<String> slotNames, PlantType plantType);

}
