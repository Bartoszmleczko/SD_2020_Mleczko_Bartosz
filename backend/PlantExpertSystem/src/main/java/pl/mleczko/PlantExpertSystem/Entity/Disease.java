package pl.mleczko.PlantExpertSystem.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "disease")
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id")
    private Integer diseaseId;

    @NonNull
    @Column(name = "name")
    private String name;

    private String templateName;


    @Column(name = "precaution", columnDefinition = "LONGTEXT")
    private String precautionDiagnose;

    @Column(name = "intervention", columnDefinition = "LONGTEXT")
    private String interventionDiagnose;

    @Column(name = "plant_type")
    @Enumerated(EnumType.STRING)
    private PlantType plantType;

    @NonNull
    @ManyToMany
    private List<Symptom> symptoms = new ArrayList<>();

    @NonNull
    @ManyToMany
    private List<RiskFactor> factors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;


}
