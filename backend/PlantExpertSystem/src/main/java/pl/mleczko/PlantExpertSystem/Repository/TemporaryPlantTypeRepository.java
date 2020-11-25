package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryPlantType;

public interface TemporaryPlantTypeRepository extends JpaRepository<TemporaryPlantType, Long> {

    public boolean existsByName(String name);

}
