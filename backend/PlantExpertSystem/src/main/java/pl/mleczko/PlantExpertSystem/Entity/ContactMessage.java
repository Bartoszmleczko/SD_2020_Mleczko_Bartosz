package pl.mleczko.PlantExpertSystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "contact_message")
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String header;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private LocalDateTime creationDate;
    private LocalDateTime answerTime;

    @Column(columnDefinition = "LONGTEXT")
    private String answer;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @JsonBackReference("userMessages")
    @ManyToOne
    private User user;

}
