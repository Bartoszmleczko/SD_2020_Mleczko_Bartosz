package pl.mleczko.PlantExpertSystem.REST;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mleczko.PlantExpertSystem.Entity.Disease;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryDisease;
import pl.mleczko.PlantExpertSystem.Model.DiseaseDto;
import pl.mleczko.PlantExpertSystem.Service.DiagnoseService;
import pl.mleczko.PlantExpertSystem.Service.DiseaseService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class DiseaseController {

    private final DiseaseService diseaseService;
    private final DiagnoseService diagnoseService;

    public DiseaseController(DiseaseService diseaseService, DiagnoseService diagnoseService) {
        this.diseaseService = diseaseService;
        this.diagnoseService = diagnoseService;
    }


    @GetMapping("/diseases")
    public Page<DiseaseDto> getAllDiseases(@RequestParam int page, @RequestParam int size){
        return diseaseService.findAll(PageRequest.of(page,size));
    }

    @GetMapping("/diseases/images")
    public List<byte[]> getImages(@RequestParam("names") List<String> names){
        return names.stream().map(name -> diagnoseService.getImage(name)).collect(Collectors.toList());
    }

    @PostMapping("/diseases")
    public TemporaryDisease addNewDisease(@RequestParam("file") MultipartFile file, @RequestParam("type") String plantType) throws IOException {

        return diseaseService.addNewDisease(file, plantType);

    }

//    @GetMapping("/diseases/images")
//    public String getUrl() throws IOException {
//        String path = new ClassPathResource("images/alternarioza_ziemniaka.jpg").toString();
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+"/"+path;
//        System.out.println(url);
//        return url;
//    }

}
