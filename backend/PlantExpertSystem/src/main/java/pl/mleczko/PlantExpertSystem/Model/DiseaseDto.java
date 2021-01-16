package pl.mleczko.PlantExpertSystem.Model;

import lombok.*;
import pl.mleczko.PlantExpertSystem.Entity.Disease;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseDto {

    private String diseaseName;
    private String diagnose;
    private String diseaseDescription;
    private Long count;

    public static DiseaseDto convertToDto(Disease disease){
        return new DiseaseDto(disease.getName(), null, disease.getDiseaseDescription(), disease.getCount());
    }

}


