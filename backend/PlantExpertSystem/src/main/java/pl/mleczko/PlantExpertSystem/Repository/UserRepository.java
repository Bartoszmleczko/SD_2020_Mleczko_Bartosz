package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.Entity.VerificationToken;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    @Query(value = "SELECT user "
            + "FROM User user "
            + "WHERE user.email = ?#{ authentication?.name }")
    User findUserProfileIdByCurrentUser();


    @Query("select u from User u where email like %?1%")
    public List<User> findAllByUsername(String email);

    public User findByVerificationToken(VerificationToken token);

    public boolean existsByEmail(String email);

}
