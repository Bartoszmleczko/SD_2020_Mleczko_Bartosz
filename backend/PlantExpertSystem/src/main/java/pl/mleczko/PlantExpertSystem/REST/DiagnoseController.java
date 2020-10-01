package pl.mleczko.PlantExpertSystem.REST;

import jess.JessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.ExpertSystem.DiseaseCreatingService;
import pl.mleczko.PlantExpertSystem.Model.DiagnoseFormDto;
import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;
import pl.mleczko.PlantExpertSystem.Model.PlantSicknessRequest;
import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;
import pl.mleczko.PlantExpertSystem.Service.DiagnoseService;
import pl.mleczko.PlantExpertSystem.Service.DiseaseDto;

import java.io.IOException;
import java.util.HashSet;

@RestController
public class DiagnoseController {

private final DiagnoseService diagnoseService;
private final DiseaseCreatingService diseaseCreatingService;

    public DiagnoseController(DiagnoseService diagnoseService, DiseaseCreatingService diseaseCreatingService) {
        this.diagnoseService = diagnoseService;
        this.diseaseCreatingService = diseaseCreatingService;
    }

    @GetMapping("/factors")
    public DiagnoseFormDto getRiskFactorsByPlantType(@RequestParam PlantType plantType){
        return diagnoseService.aggregateRiskFactorsAndSymptomsForForm(plantType);
    }

    @PostMapping(value = "/factors", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HashSet<DiseaseDto> provideFactsForEvaluation(@RequestBody PlantSicknessRequest request) throws JessException {
        return diagnoseService.diagnose(request);
    }

    @PostMapping("/diseases")
    public String addNewDisease(NewDiseaseForm form) throws IOException {
        diseaseCreatingService.createNewDisease(form);
    }

}
