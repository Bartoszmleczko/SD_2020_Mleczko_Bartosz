package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
}
