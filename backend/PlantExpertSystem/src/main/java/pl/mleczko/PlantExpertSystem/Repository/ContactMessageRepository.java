package pl.mleczko.PlantExpertSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczko.PlantExpertSystem.Entity.ContactMessage;
import pl.mleczko.PlantExpertSystem.Entity.MessageStatus;
import pl.mleczko.PlantExpertSystem.Entity.User;

import java.util.List;

@CrossOrigin(origins = "*")
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    public List<ContactMessage> findAllByUserOrderByCreationDateDesc(User user);

    public List<ContactMessage> findAllByStatusOrderByCreationDateDesc(MessageStatus status);

}
