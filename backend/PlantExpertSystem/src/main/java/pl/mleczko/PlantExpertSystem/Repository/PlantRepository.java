package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
