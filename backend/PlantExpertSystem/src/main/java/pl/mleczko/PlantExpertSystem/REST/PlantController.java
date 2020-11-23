package pl.mleczko.PlantExpertSystem.REST;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryPlantType;
import pl.mleczko.PlantExpertSystem.Service.TemporaryPlantTypeService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PlantController {

    private final TemporaryPlantTypeService temporaryPlantTypeService;

    public PlantController(TemporaryPlantTypeService temporaryPlantTypeService) {
        this.temporaryPlantTypeService = temporaryPlantTypeService;
    }

    @GetMapping("/plantRequests")
    public List<TemporaryPlantType> getAllRequests(){
        return this.temporaryPlantTypeService.findAll();
    }

    @PostMapping("/plantRequests")
    public TemporaryPlantType save(@RequestBody TemporaryPlantType plantType){
        return temporaryPlantTypeService.save(plantType);
    }

    @PutMapping("/plantRequests")
    public PlantType acceptRequest(@RequestBody TemporaryPlantType plantType){
        return temporaryPlantTypeService.acceptRequest(plantType);
    }

    @DeleteMapping("/plantRequests")
    public ResponseEntity<String> declineRequest(@RequestParam Long id){
        temporaryPlantTypeService.delete(id);
        return ResponseEntity.ok("Usunięto roślinę z listy tymczasowej.");
    }

}
