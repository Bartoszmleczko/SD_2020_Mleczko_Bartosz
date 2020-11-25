package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.PlantType;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Exception.ObjectAlreadyExists;
import pl.mleczko.PlantExpertSystem.Repository.PlantTypeRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PlantTypeService {

    private final PlantTypeRepository plantTypeRepository;

    public PlantTypeService(PlantTypeRepository plantTypeRepository) {
        this.plantTypeRepository = plantTypeRepository;
    }

    @Transactional
    public PlantType save (PlantType type){
        type.setName(type.getName().toUpperCase());
        if(plantTypeRepository.existsByName(type.getName())){
            throw new ObjectAlreadyExists(PlantType.class.getSimpleName());
        }else{
            return plantTypeRepository.save(type);
        }

    }

    @Transactional
    public List<PlantType> findAll(){
        return plantTypeRepository.findAll();
    }

    @Transactional
    public PlantType findByName(String name){
        return plantTypeRepository.findByName(name);
    }

    @Transactional
    public PlantType findById(Long id ){
        return plantTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(PlantType.class.getSimpleName()));
    }

}

