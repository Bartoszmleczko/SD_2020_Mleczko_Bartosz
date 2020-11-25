package pl.mleczko.PlantExpertSystem.REST;

import com.squareup.okhttp.Response;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mleczko.PlantExpertSystem.Entity.ContactMessage;
import pl.mleczko.PlantExpertSystem.Model.ContactMessageDto;
import pl.mleczko.PlantExpertSystem.Service.ContactMessageService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins= "*")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping("/users/messages")
    public ResponseEntity<List<ContactMessageDto>> findAllCurrentUserMessages(Principal principal){
        return ResponseEntity.ok(contactMessageService.findAllCurrentUserMessages(principal.getName()));
    }

    @GetMapping("/messages/unanswered")
    public ResponseEntity<List<ContactMessageDto>> findAllUnanswered(){
        return ResponseEntity.ok(contactMessageService.findAllUnanswered());
    }

    @PostMapping("/messages")
    public ResponseEntity<ContactMessage> saveNewMessage(@RequestBody ContactMessage message, Principal principal){
        return ResponseEntity.ok(contactMessageService.save(message,principal.getName()));
    }

    @GetMapping("/messages/answered")
    public ResponseEntity<List<ContactMessageDto>> findAllAnswered(){
        return ResponseEntity.ok(contactMessageService.findAllAnswered());
    }

    @DeleteMapping("messages/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id){
        contactMessageService.deleteMessage(id);
        return ResponseEntity.ok("Usunięto wiadomość");
    }

    @PutMapping("/messages")
    public ResponseEntity<ContactMessageDto> answerMessage(@RequestBody ContactMessageDto dto, Principal principal){
        return ResponseEntity.ok(contactMessageService.answerMessage(dto,principal));
    }

}
