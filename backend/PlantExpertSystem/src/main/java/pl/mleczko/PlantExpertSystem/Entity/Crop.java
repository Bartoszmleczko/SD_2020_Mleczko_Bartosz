package pl.mleczko.PlantExpertSystem.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "crop")
@Entity
@Data
@NoArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_id")
    private Integer cropId;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "area")
    private Double area;

    @OneToMany
    private Set<Plant> plants = new HashSet<>();


}
