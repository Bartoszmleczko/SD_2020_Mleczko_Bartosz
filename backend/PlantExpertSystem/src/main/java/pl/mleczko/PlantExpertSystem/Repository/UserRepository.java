package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczko.PlantExpertSystem.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

}
