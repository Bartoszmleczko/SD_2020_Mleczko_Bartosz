package pl.mleczko.PlantExpertSystem.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PlantSicknessRequest {

    private Set<RequestSlotDto> riskFactors = new HashSet<>();
    private Set<RequestSlotDto> symptoms = new HashSet<>();


}
