package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.Diagnose;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;
import pl.mleczko.PlantExpertSystem.Entity.Symptom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiagnoseDto {

    private Long id;

    private LocalDateTime creationTime;

    private String note;

    private Set<DiseaseDto> diseases;

    private List<RiskFactor> riskFactors;

    private List<Symptom> symptoms;

    public static DiagnoseDto convertToDto(Diagnose diagnose){
        DiagnoseDto dto = new DiagnoseDto();
        dto.setId(diagnose.getId());
        if(diagnose.getNote() != null)
        dto.setNote(diagnose.getNote());
        dto.setCreationTime(diagnose.getCreationTime());
        dto.setDiseases(diagnose.getDiseases().stream().map(d -> DiseaseDto.convertToDto(d)).collect(Collectors.toSet()));
        dto.setRiskFactors(diagnose.getRiskFactors());
        dto.setSymptoms(diagnose.getSymptoms());
        return dto;
    }

}
