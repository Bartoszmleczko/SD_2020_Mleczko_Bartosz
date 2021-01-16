package pl.mleczko.PlantExpertSystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @JsonBackReference(value = "userDiagnoses")
    @ManyToOne
    private User user;

    @JsonBackReference(value = "diseaseReference")
    @ManyToMany
    private List<Disease> diseases;

    @ManyToMany
    @JoinTable(name = "diagnose_risk_factor",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "riskId") })
    private List<RiskFactor> riskFactors = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "diagnose_symptom",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "symptomId") })
    private List<Symptom> symptoms = new ArrayList<>();

}
