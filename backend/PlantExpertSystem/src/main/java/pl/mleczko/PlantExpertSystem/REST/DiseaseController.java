package pl.mleczko.PlantExpertSystem.REST;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczko.PlantExpertSystem.Entity.Disease;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryDisease;
import pl.mleczko.PlantExpertSystem.ExpertSystem.DiseaseCreatingService;
import pl.mleczko.PlantExpertSystem.Model.DiseaseDto;
import pl.mleczko.PlantExpertSystem.Model.RuleTemplate;
import pl.mleczko.PlantExpertSystem.Model.TemporaryDiseaseTemplateForm;
import pl.mleczko.PlantExpertSystem.Service.DiagnoseService;
import pl.mleczko.PlantExpertSystem.Service.DiseaseService;
import pl.mleczko.PlantExpertSystem.Service.FileStorageService;
import pl.mleczko.PlantExpertSystem.Service.TemporaryDiseaseService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class DiseaseController {

    private final DiseaseService diseaseService;
    private final DiagnoseService diagnoseService;
    private final DiseaseCreatingService diseaseCreatingService;
    private final TemporaryDiseaseService temporaryDiseaseService;
    private final FileStorageService fileStorageService;

    public DiseaseController(DiseaseService diseaseService, DiagnoseService diagnoseService, DiseaseCreatingService diseaseCreatingService, TemporaryDiseaseService temporaryDiseaseService, FileStorageService fileStorageService) {
        this.diseaseService = diseaseService;
        this.diagnoseService = diagnoseService;
        this.diseaseCreatingService = diseaseCreatingService;
        this.temporaryDiseaseService = temporaryDiseaseService;
        this.fileStorageService = fileStorageService;
    }


    @GetMapping("/diseases")
    public Page<DiseaseDto> getAllDiseases(@RequestParam int page, @RequestParam int size, @RequestParam String plant){
        return diseaseService.findAll(PageRequest.of(page,size), plant);
    }

    @GetMapping("/diseases/fromDtos")
    public ResponseEntity<List<Disease>> getDiseases(@RequestParam List<String> diseaseNames){
        return ResponseEntity.ok(diseaseService.findAllByName(diseaseNames));
    }

    @GetMapping("/diseases/images")
    public List<byte[]> getImages(@RequestParam("names") List<String> names){
        return names.stream().map(name -> diagnoseService.getImage(name)).collect(Collectors.toList());
    }

    @PostMapping(path = "/diseases", consumes = {"multipart/form-data"})
    public ResponseEntity<TemporaryDisease> addNewDisease(@ModelAttribute TemporaryDiseaseTemplateForm form) throws IOException {

        return ResponseEntity.ok(diseaseService.addNewDisease(form));

    }

    @PostMapping("/diseases/accept")
    public Disease disease(@RequestBody TemporaryDisease temporaryDisease) throws IOException {
        return diseaseCreatingService.createNewDisease(temporaryDisease );
    }

    @GetMapping("/tempDiseases")
    public ResponseEntity<List<TemporaryDisease>> findAll(){
        return ResponseEntity.ok(temporaryDiseaseService.findAll());
    }

    @DeleteMapping("/diseases/discard")
    public ResponseEntity<String> deleteTemporaryDisease(Long id){
        temporaryDiseaseService.deleteById(id);
        TemporaryDisease disease=temporaryDiseaseService.findById(id);
        if(disease != null){
            fileStorageService.deleteImage(disease.getImageUrl());
        }
        return ResponseEntity.ok("Pomyślnie usunięto");
    }

    @GetMapping("/diseases/top")
    public ResponseEntity<List<DiseaseDto>> getTop5Diseases(){
        return ResponseEntity.ok(diagnoseService.find5TopDiseases());
    }


//    @GetMapping("/diseases/images")
//    public String getUrl() throws IOException {
//        String path = new ClassPathResource("images/alternarioza_ziemniaka.jpg").toString();
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+"/"+path;
//        System.out.println(url);
//        return url;
//    }


    //chwościk buraka, czekoladowa plamistość liści na bobiku (burak), zgnilizna twardzikowa soi, rizoktonioza ziemniaka, czerń krzyżowych rzepaku, szara pleśń rzepaku

}
