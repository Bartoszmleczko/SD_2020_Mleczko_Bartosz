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
    private Long diseaseId;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    private String templateName;

    @NonNull
    @Column(name = "precaution", columnDefinition = "LONGTEXT")
    private String precautionDiagnose;

    @NonNull
    @Column(name = "intervention", columnDefinition = "LONGTEXT")
    private String interventionDiagnose;

    @Column(name ="image_name")
    private String imageName;

    @NonNull
    @Column(name = "disease_description")
    private String diseaseDescription;


    @ManyToOne
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
