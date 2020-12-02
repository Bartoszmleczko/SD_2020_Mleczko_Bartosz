package pl.mleczko.PlantExpertSystem.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PlantSicknessRequest {

    @NotNull
    private Set<RequestSlotDto> riskFactors = new HashSet<>();
    @NotNull
    private Set<RequestSlotDto> symptoms = new HashSet<>();
    @NotNull
    private String plant;

}
