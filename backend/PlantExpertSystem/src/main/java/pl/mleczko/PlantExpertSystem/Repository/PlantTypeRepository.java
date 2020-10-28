package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;

public interface  PlantTypeRepository extends JpaRepository<PlantType, Long> {

    public PlantType findByName(String name);


}



