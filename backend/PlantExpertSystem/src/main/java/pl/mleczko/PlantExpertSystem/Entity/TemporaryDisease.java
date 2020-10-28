package pl.mleczko.PlantExpertSystem.Entity;


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

    @ElementCollection
    @CollectionTable(name = "rules", joinColumns = @JoinColumn(name = "id"))
    @Column(columnDefinition = "LONGTEXT")
    private List<String> rules;

    @ElementCollection
    @CollectionTable(name = "jess_rules", joinColumns = @JoinColumn(name = "id"))
    @Column(columnDefinition = "LONGTEXT")
    private List<String> jessRules;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "precaution", columnDefinition = "LONGTEXT")
    private String precautionDiagnose;


    @Column(name = "intervention", columnDefinition = "LONGTEXT")
    private String interventionDiagnose;

    @ManyToOne
    private PlantType plantType;

    @Column
    private LocalDateTime requestDate;

}
