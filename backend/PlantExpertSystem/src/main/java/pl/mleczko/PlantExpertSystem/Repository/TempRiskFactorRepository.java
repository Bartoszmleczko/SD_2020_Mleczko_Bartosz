package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.TempRiskFactor;

public interface TempRiskFactorRepository extends JpaRepository<TempRiskFactor, Integer> {
}
