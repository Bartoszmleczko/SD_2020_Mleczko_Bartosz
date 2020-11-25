package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import pl.mleczko.PlantExpertSystem.Entity.ContactMessage;
import pl.mleczko.PlantExpertSystem.Entity.MessageStatus;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Model.ContactMessageDto;
import pl.mleczko.PlantExpertSystem.Repository.ContactMessageRepository;
import pl.mleczko.PlantExpertSystem.Repository.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final UserService userService;
    private final MailService mailService;
    private final UserRepository userRepository;

    public ContactMessageService(ContactMessageRepository contactMessageRepository, UserService userService, MailService mailService, UserRepository userRepository) {
        this.contactMessageRepository = contactMessageRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    @Transactional
    public ContactMessage save(ContactMessage message, String username){
        User user = userService.findByUsername(username);
        message.setCreationDate(LocalDateTime.now());
        user.getMessages().add(message);
        message.setUser(user);
        message.setStatus(MessageStatus.DOSTARCZONO);
        user = userRepository.save(user);
        return message;
    }

    @Transactional
    public ContactMessage findById(Long id){
        return contactMessageRepository.findById(id).orElseThrow(() -> new NotFoundException(ContactMessage.class.getSimpleName()));
    }

    @Transactional
    public List<ContactMessageDto> findAllCurrentUserMessages(String username){
        User user = userService.findByUsername(username);
        return ContactMessageDto.convertToListDto(contactMessageRepository.findAllByUserOrderByCreationDateDesc(user));
    }

    @Transactional
    public List<ContactMessageDto> findAllUnanswered(){
        List<ContactMessageDto> dtos = ContactMessageDto.convertToListDto(contactMessageRepository.findAllByStatusOrderByCreationDateDesc(MessageStatus.DOSTARCZONO));
        return dtos;
    }

    @Transactional
    public List<ContactMessageDto> findAllAnswered(){
        List<ContactMessageDto> dtos = ContactMessageDto.convertToListDto(contactMessageRepository.findAllByStatusOrderByCreationDateDesc(MessageStatus.ODPOWIEDZIANO));
        return dtos;
    }

    @Transactional
    public void deleteMessage(Long id){
        contactMessageRepository.deleteById(id);
    }

    @Transactional
    public ContactMessageDto answerMessage(ContactMessageDto dto, Principal principal){
        User admin = userService.findByUsername(principal.getName());
        ContactMessage message = findById(dto.getId());
        message.setAnswer(dto.getAnswer());
        message.setStatus(MessageStatus.ODPOWIEDZIANO);
        message.setAnswerTime(LocalDateTime.now());
        Context c = mailService.prepareAnswerEmailContext(message.getHeader(), message.getContent(), message.getAnswer(), admin.getFirstName(), admin.getLastName());
        mailService.sendEmail(message.getUser().getEmail(), "Odpowiedź - PlantExpert", mailService.prepareContactMessageAnswerEmail(c));
        return ContactMessageDto.convertToDto(contactMessageRepository.save(message));
    }

}
