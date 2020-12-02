package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.TempDiseaseStatus;
import pl.mleczko.PlantExpertSystem.Entity.TemporaryDisease;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Repository.TemporaryDiseaseRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
public class TemporaryDiseaseService {

    private final TemporaryDiseaseRepository temporaryDiseaseRepository;
    private final FileStorageService fileStorageService;
    private final UserService userService;

    public TemporaryDiseaseService(TemporaryDiseaseRepository temporaryDiseaseRepository, FileStorageService fileStorageService, UserService userService) {
        this.temporaryDiseaseRepository = temporaryDiseaseRepository;
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @Transactional
    public TemporaryDisease save(TemporaryDisease temporaryDisease){
        return temporaryDiseaseRepository.save(temporaryDisease);
    }

    @Transactional
    public TemporaryDisease findById(Long id){
        return temporaryDiseaseRepository.findById(id).orElseThrow(() -> new NotFoundException(TemporaryDisease.class.getSimpleName()));
    }

    @Transactional
    public List<TemporaryDisease> findAll(){
        return temporaryDiseaseRepository.findAll();
    }


    @Transactional
    public List<TemporaryDisease> findAllCurrentUserRefusedDiseases(Principal principal){
        User user =  userService.findByUsername(principal.getName());
        return temporaryDiseaseRepository.findAllByTempDiseaseStatusAndUser(TempDiseaseStatus.ODRZUCONA, user);
    }

    @Transactional
    public void deleteById(Long id){
        temporaryDiseaseRepository.deleteById(id);
    }

    @Transactional
    public TemporaryDisease findByTemplateName(String templateName){
        return temporaryDiseaseRepository.findByDiseaseTemplateName(templateName);
    }

    @Transactional
    public List<TemporaryDisease> findAllByStatus(TempDiseaseStatus status){
        return temporaryDiseaseRepository.findAllByTempDiseaseStatus(status);
    }

}
