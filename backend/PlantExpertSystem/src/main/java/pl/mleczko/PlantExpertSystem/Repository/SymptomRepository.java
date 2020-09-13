package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.Symptom;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    public List<Symptom> findAllBySymptomIdIsBetween(Integer start, Integer end);

}
