package pl.mleczko.PlantExpertSystem.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "symptom")
@Data
@NoArgsConstructor
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "symptom_id")
    private Long symptomId;

    @Column(name = "name")
    private String name;

    @Column(name = "slot_name")
    private String slotName;

    @Column(name = "plant_type")
    @Enumerated(EnumType.STRING)
    private PlantType plantType;

}
