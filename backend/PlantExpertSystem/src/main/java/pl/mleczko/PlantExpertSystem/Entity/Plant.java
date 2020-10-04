package pl.mleczko.PlantExpertSystem.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column Long plantId;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plant")
    private Set<Disease> diseases;

}
