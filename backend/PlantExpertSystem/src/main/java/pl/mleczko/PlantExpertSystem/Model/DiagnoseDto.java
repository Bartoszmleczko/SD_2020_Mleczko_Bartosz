package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.Diagnose;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiagnoseDto {

    private Long id;

    private LocalDateTime creationTime;

    private String note;

    private Set<DiseaseDto> diseases;


    public static DiagnoseDto convertToDto(Diagnose diagnose){
        DiagnoseDto dto = new DiagnoseDto();
        dto.setId(diagnose.getId());
        if(diagnose.getNote() != null)
        dto.setNote(diagnose.getNote());
        dto.setCreationTime(diagnose.getCreationTime());
        dto.setDiseases(diagnose.getDiseases().stream().map(d -> DiseaseDto.convertToDto(d)).collect(Collectors.toSet()));
        return dto;
    }

}
