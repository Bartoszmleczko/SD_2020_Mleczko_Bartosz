package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    public Disease findByTemplateName(String templateName);

    public boolean existsByTemplateName(String templateName);

}
