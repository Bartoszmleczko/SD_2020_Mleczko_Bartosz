package pl.mleczko.PlantExpertSystem.Service;

import jess.JessException;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Exception.FileNotFoundException;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.ExpertSystem.PlantExpertEvalService;
import pl.mleczko.PlantExpertSystem.Model.DiagnoseDto;
import pl.mleczko.PlantExpertSystem.Model.DiagnoseFormDto;
import pl.mleczko.PlantExpertSystem.Model.DiseaseDto;
import pl.mleczko.PlantExpertSystem.Model.PlantSicknessRequest;
import pl.mleczko.PlantExpertSystem.Repository.DiagnoseRepository;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;
import pl.mleczko.PlantExpertSystem.Repository.RiskFactorRepository;
import pl.mleczko.PlantExpertSystem.Repository.SymptomRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnoseService {

    private final RiskFactorRepository riskFactorRepository;
    private final SymptomRepository symptomRepository;
    private final PlantExpertEvalService plantExpertEvalService;
    private final DiseaseRepository diseaseRepository;
    private final FileStorageService fileStorageService;
    private final PlantTypeService plantTypeService;
    private final UserService userService;
    private final DiagnoseRepository diagnoseRepository;

    public DiagnoseService(RiskFactorRepository riskFactorRepository, SymptomRepository symptomRepository,
                           PlantExpertEvalService plantExpertEvalService, DiseaseRepository diseaseRepository,
                           FileStorageService fileStorageService, PlantTypeService plantTypeService,
                           UserService userService, DiagnoseRepository diagnoseRepository) {
        this.riskFactorRepository = riskFactorRepository;
        this.symptomRepository = symptomRepository;
        this.plantExpertEvalService = plantExpertEvalService;
        this.diseaseRepository = diseaseRepository;
        this.fileStorageService = fileStorageService;
        this.plantTypeService = plantTypeService;
        this.userService = userService;
        this.diagnoseRepository = diagnoseRepository;
    }

    @Transactional
    public HashMap<FactorType, HashSet<RiskFactor>> getAllFactorsByTypeAndPlantType(String plantTypeName){
        HashMap<FactorType, HashSet<RiskFactor>> result = new HashMap<>();
        PlantType plantType = plantTypeService.findByName(plantTypeName);

        for(FactorType type : FactorType.values()){
            HashSet<RiskFactor> factors = new HashSet<>(riskFactorRepository.findAllByPlantTypeAndFactorType(plantType,type));
            result.put(type,factors);
        }

        return result;
    }

    @Transactional
    public HashSet<Symptom> getAllSymptomsByPlantType(String name){
        PlantType plantType = plantTypeService.findByName(name);

        return new HashSet<>(symptomRepository.findAllByPlantType(plantType));
    }

    public DiagnoseFormDto aggregateRiskFactorsAndSymptomsForForm(String name){
        DiagnoseFormDto formDto = new DiagnoseFormDto();

        formDto.setRiskFactors(getAllFactorsByTypeAndPlantType(name));
        formDto.setSymptoms(getAllSymptomsByPlantType(name));

        return formDto;
    }

    @Transactional
    public Disease findDiseaseByTemplateName(String templateName){
        return diseaseRepository.findByTemplateName(templateName);
    }

    public HashSet<DiseaseDto> diagnose(PlantSicknessRequest plantSicknessRequest) throws JessException, IOException {
        ArrayList<String> jessResults = (ArrayList<String>) plantExpertEvalService.provideFact(plantSicknessRequest);
        HashSet<DiseaseDto> finalResult = new HashSet<>();

        for(String res : jessResults){

            String[] resultParts = res.split(":");
            String diseaseTemplateName = res.split(":")[0];
            String diagnoseType = res.split(":")[1];



            Disease disease  = findDiseaseByTemplateName(diseaseTemplateName);

            String diagnose = pickDiagnose(disease, diagnoseType);

            DiseaseDto dto = new DiseaseDto(disease.getName(), diagnose,disease.getDiseaseDescription(),disease.getCount());
            finalResult.add(dto);
        }
        return finalResult;
    }

    private String pickDiagnose(Disease disease, String diagnoseType) {

        if(diagnoseType.equals("interw"))
            return disease.getInterventionDiagnose();
        else return disease.getPrecautionDiagnose();
    }


    public byte[] getImage(String diseaseName) {
        Disease disease = diseaseRepository.findByName(diseaseName);
        byte[] image = fileStorageService.getImageFile(disease.getImageName());
        if(image != null) {
            return fileStorageService.getImageFile(disease.getImageName());
        } else{
            throw new FileNotFoundException();
        }
    }

    @Transactional
    public Diagnose findById(Long id){
        return diagnoseRepository.findById(id).orElseThrow(() -> new NotFoundException(Diagnose.class.getSimpleName()));
    }

    @Transactional
    public Diagnose updateDiagnose(DiagnoseDto dto){

        Diagnose diagnose = findById(dto.getId());
        diagnose.setNote(dto.getNote());

        return diagnoseRepository.save(diagnose);
    }

    @Transactional
    public Diagnose saveDiagnose(DiagnoseDto dto, String username){
        System.out.println(username);
        User user = userService.findByUsername(username);
        Diagnose diagnose = new Diagnose();
        diagnose.setUser(user);
        List<Disease> diseases = dto.getDiseases().stream().map(d -> diseaseRepository.findByName(d.getDiseaseName())).collect(Collectors.toList());
        diseases.forEach(d -> d.setCount(d.getCount()+1));
        diseaseRepository.saveAll(diseases);
        diagnose.setDiseases(diseases);
        diagnose.setCreationTime(LocalDateTime.now());
        return diagnoseRepository.save(diagnose);
    }


    @Transactional
    public List<DiagnoseDto> findAllCurrentUserDiagnoses(Principal principal){
        User user = userService.findByUsername(principal.getName());
        List<Diagnose> diagnoses = diagnoseRepository.findAllByUserOrderByCreationTimeDesc(user);
        return diagnoses.stream().map(d -> DiagnoseDto.convertToDto(d)).collect(Collectors.toList());
    }

    @Transactional
    public List<DiseaseDto> find5TopDiseases(){
        List<Disease> diseases = diseaseRepository.findTop5ByOrderByCountDesc();
        return diseases.stream().map( d -> DiseaseDto.convertToDto(d)).collect(Collectors.toList());
    }



}
