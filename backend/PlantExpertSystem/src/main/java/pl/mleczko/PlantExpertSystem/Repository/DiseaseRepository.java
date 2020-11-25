package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mleczko.PlantExpertSystem.Entity.Disease;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;

import java.util.List;


public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    public Disease findByTemplateName(String templateName);

    public boolean existsByTemplateName(String templateName);


    public Disease findByName(String name);

    public List<Disease> findAllByPlantType(PlantType plantType);

    public Page<Disease> findAllByPlantType(Pageable pageable, PlantType plantType);

    public Page<Disease> findAll(Pageable pageable);

    public Disease findByNameOrTemplateNameAndPlantType(String name, String templateName, PlantType type);

    public List<Disease> findTop5ByOrderByCountDesc();

}
