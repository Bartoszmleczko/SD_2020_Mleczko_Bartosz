package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.ContactMessage;
import pl.mleczko.PlantExpertSystem.Entity.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContactMessageDto {

    private Long id;
    private String header;
    private String content;
    private LocalDateTime creationDate;
    private String answer;
    private LocalDateTime answerTime;
    private MessageStatus status;
    private String email;
    private String firstName;
    private String lastName;

    public static ContactMessageDto convertToDto(ContactMessage message){
        ContactMessageDto dto = new ContactMessageDto();
        dto.setId(message.getId());
        dto.setHeader(message.getHeader());
        dto.setContent(message.getContent());
        if(message.getAnswerTime() != null)
            dto.setAnswerTime(message.getAnswerTime());
        if(message.getAnswer() != null)
            dto.setAnswer(message.getAnswer());
        dto.setCreationDate(message.getCreationDate());
        dto.setStatus(message.getStatus());
        dto.setEmail(message.getUser().getEmail());
        dto.setFirstName(message.getUser().getFirstName());
        dto.setLastName(message.getUser().getLastName());
        return dto;
    }

    public static List<ContactMessageDto> convertToListDto(List<ContactMessage> messages){
        return messages.stream().map(m -> convertToDto(m)).collect(Collectors.toList());
    }



}
