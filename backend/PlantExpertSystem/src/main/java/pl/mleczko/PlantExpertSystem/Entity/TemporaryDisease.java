package pl.mleczko.PlantExpertSystem.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;
import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "temporary_disease")
public class TemporaryDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String diseaseName;

    @Column
    private String diseaseTemplateName;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<TempRiskFactor> riskFactors;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<TempSymptom> symptoms;


    @OneToMany(cascade = CascadeType.ALL)
    private List<TempRule> rules;

    @ElementCollection
    @CollectionTable(name = "jess_rules", joinColumns = @JoinColumn(name = "id"))
    @Column(columnDefinition = "LONGTEXT")
    private List<String> jessRules;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "precaution", columnDefinition = "LONGTEXT")
    private String precautionDiagnose;

    @Enumerated(EnumType.STRING)
    private TempDiseaseStatus tempDiseaseStatus;

    @Column(name = "intervention", columnDefinition = "LONGTEXT")
    private String interventionDiagnose;

    @ManyToOne
    private PlantType plantType;

    @Column
    private LocalDateTime requestDate;

    @Column
    private String imageUrl;

    @JsonBackReference("userTemporaryDiseases")
    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private TemporaryDiseaseRefuseProperties properties;

}
