package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mleczko.PlantExpertSystem.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    @Query(value = "SELECT user "
            + "FROM User user "
            + "WHERE user.email = ?#{ authentication?.name }")
    User findUserProfileIdByCurrentUser();


}
