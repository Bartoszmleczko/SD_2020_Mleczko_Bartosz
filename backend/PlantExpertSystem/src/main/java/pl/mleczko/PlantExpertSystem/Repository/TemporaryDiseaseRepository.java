package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.TempDiseaseStatus;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryDisease;
import pl.mleczko.PlantExpertSystem.Entity.User;

import java.util.List;

public interface TemporaryDiseaseRepository extends JpaRepository<TemporaryDisease, Long> {

    public TemporaryDisease findByDiseaseTemplateName(String templateName);

    public List<TemporaryDisease> findAllByTempDiseaseStatus(TempDiseaseStatus status);

    public List<TemporaryDisease> findAllByTempDiseaseStatusAndUser(TempDiseaseStatus status, User user );

    public TemporaryDisease findByDiseaseName(String name);

}
