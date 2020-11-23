package pl.mleczko.PlantExpertSystem.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "temp_risk_factor")
public class TempRiskFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String templateName;

    @Enumerated(EnumType.STRING)
    private FactorType factorType;


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        if(id != null)
        result = 31 * result + id;
        result = 31 * result + templateName.hashCode();
        result = 31 * result + factorType.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof TempRiskFactor)) {
            return false;
        }
        TempRiskFactor f = (TempRiskFactor) obj;

        return name.equals(f.getName()) && templateName.equals(f.getTemplateName()) && id == f.getId() && factorType == f.getFactorType();
    }
}
