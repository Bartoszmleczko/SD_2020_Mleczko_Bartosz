package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryDisease;

public interface TemporaryDiseaseRepository extends JpaRepository<TemporaryDisease, Integer> {
}
