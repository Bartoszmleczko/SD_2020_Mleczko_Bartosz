package pl.mleczko.PlantExpertSystem.REST;

import jess.JessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczko.PlantExpertSystem.Entity.Diagnose;
import pl.mleczko.PlantExpertSystem.Entity.FactorType;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.ExpertSystem.DiseaseCreatingService;
import pl.mleczko.PlantExpertSystem.Model.*;
import pl.mleczko.PlantExpertSystem.Service.DiagnoseService;
import pl.mleczko.PlantExpertSystem.Service.DiseaseService;
import pl.mleczko.PlantExpertSystem.Service.FileStorageService;
import pl.mleczko.PlantExpertSystem.Service.PlantTypeService;

import java.io.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DiagnoseController {

private final DiagnoseService diagnoseService;
private final DiseaseCreatingService diseaseCreatingService;
private final FileStorageService fileStorageService;
private final PlantTypeService plantTypeService;
private final DiseaseService diseaseService;

    public DiagnoseController(DiagnoseService diagnoseService, DiseaseCreatingService diseaseCreatingService,
                              FileStorageService fileStorageService, PlantTypeService plantTypeService, DiseaseService diseaseService) {
        this.diagnoseService = diagnoseService;
        this.diseaseCreatingService = diseaseCreatingService;
        this.fileStorageService = fileStorageService;
        this.plantTypeService = plantTypeService;
        this.diseaseService = diseaseService;
    }

    @GetMapping("/plantTypes")
    public List<PlantType> findAll(){
        return plantTypeService.findAll();
    }

    @GetMapping("/factors")
    public DiagnoseFormDto getRiskFactorsByPlantType(@RequestParam String plantType){
        return diagnoseService.aggregateRiskFactorsAndSymptomsForForm(plantType);
    }

    @PostMapping(value = "/factors", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HashSet<DiseaseDto> provideFactsForEvaluation(@RequestBody PlantSicknessRequest request) throws JessException, IOException {
        return diagnoseService.diagnose(request);
    }

    @GetMapping("/disease")
    public ResponseEntity<HttpStatus> addNewDisease() throws IOException {

        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @GetMapping("/images")
    public byte[] getImageUrl(@RequestParam("name") String name) throws IOException {
        return diagnoseService.getImage(name);
    }

    @GetMapping("/factorTypes")
    public ResponseEntity<FactorType[]> getAllFactorTypes(){
        return ResponseEntity.ok(FactorType.values());
    }


    //TODO save diagnose check active user profile
    @PostMapping("/diagnoses")
        @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Diagnose> saveDiagnose(@RequestBody DiagnoseDto dto, Principal principal){
        return ResponseEntity.ok(diagnoseService.saveDiagnose(dto, principal.getName()));
    }

    @PutMapping("/diagnoses")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Diagnose> updateDiagnose(@RequestBody DiagnoseDto dto){
        return ResponseEntity.ok(diagnoseService.updateDiagnose(dto));
    }

    @GetMapping("/diagnoses/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<DiagnoseDto>> findUsersDiagnoses(Principal principal){
        return ResponseEntity.ok(diagnoseService.findAllCurrentUserDiagnoses(principal));
    }





}
