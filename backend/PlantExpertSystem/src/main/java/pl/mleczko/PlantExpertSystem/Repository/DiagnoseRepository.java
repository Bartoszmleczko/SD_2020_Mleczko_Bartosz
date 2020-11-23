package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.Diagnose;
import pl.mleczko.PlantExpertSystem.Entity.User;

import java.util.List;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {

    public List<Diagnose> findAllByUserOrderByCreationTimeDesc(User user);

}
