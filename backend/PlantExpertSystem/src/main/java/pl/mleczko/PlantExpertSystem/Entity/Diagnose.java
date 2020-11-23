package pl.mleczko.PlantExpertSystem.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationTime;

    @Column(name = "note", columnDefinition = "LONGTEXT")
    private String note;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User user;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "diagnose_disease", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "diseaseId"))
    private List<Disease> diseases;

}
