package pl.mleczko.PlantExpertSystem.REST;

import jess.JessException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.ExpertSystem.DiseaseCreatingService;
import pl.mleczko.PlantExpertSystem.Model.*;
import pl.mleczko.PlantExpertSystem.Service.DiagnoseService;
import pl.mleczko.PlantExpertSystem.Service.FileStorageService;

import javax.annotation.Resource;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

@RestController
@CrossOrigin(origins = "*")
public class DiagnoseController {

private final DiagnoseService diagnoseService;
private final DiseaseCreatingService diseaseCreatingService;
private final FileStorageService fileStorageService;

    public DiagnoseController(DiagnoseService diagnoseService, DiseaseCreatingService diseaseCreatingService, FileStorageService fileStorageService) {
        this.diagnoseService = diagnoseService;
        this.diseaseCreatingService = diseaseCreatingService;
        this.fileStorageService = fileStorageService;
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
    public ResponseEntity<HttpStatus> addNewDisease(NewDiseaseForm form) throws IOException {
//       return  ResponseEntity.ok(diseaseCreatingService.createNewDisease(form));
        diseaseCreatingService.writeRiskFactor(new SimpleTemplateForm());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  String retrieveImage(@RequestParam("image") MultipartFile image){
        fileStorageService.store(image);
        return "created";
    }

    @GetMapping("/images")
    public byte[] getImageUrl() throws IOException {
        File file = new File("src/main/resources/images/cereal-1866559.jpg");
        return FileUtil.readAsByteArray(file);
    }

}
