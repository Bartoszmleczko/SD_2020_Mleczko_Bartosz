package pl.mleczko.PlantExpertSystem.Service;

import jess.JessException;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Exception.FileNotFoundException;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.ExpertSystem.PlantExpertEvalService;
import pl.mleczko.PlantExpertSystem.Model.*;
import pl.mleczko.PlantExpertSystem.Repository.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
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
    private final TemporaryDiseaseRepository temporaryDiseaseRepository;

    public DiagnoseService(RiskFactorRepository riskFactorRepository, SymptomRepository symptomRepository,
                           PlantExpertEvalService plantExpertEvalService, DiseaseRepository diseaseRepository,
                           FileStorageService fileStorageService, PlantTypeService plantTypeService,
                           UserService userService, DiagnoseRepository diagnoseRepository, TemporaryDiseaseRepository temporaryDiseaseRepository) {
        this.riskFactorRepository = riskFactorRepository;
        this.symptomRepository = symptomRepository;
        this.plantExpertEvalService = plantExpertEvalService;
        this.diseaseRepository = diseaseRepository;
        this.fileStorageService = fileStorageService;
        this.plantTypeService = plantTypeService;
        this.userService = userService;
        this.diagnoseRepository = diagnoseRepository;
        this.temporaryDiseaseRepository = temporaryDiseaseRepository;
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

    public DiagnoseDto diagnose(PlantSicknessRequest plantSicknessRequest) throws JessException, IOException {
        ArrayList<String> jessResults = (ArrayList<String>) plantExpertEvalService.provideFact(plantSicknessRequest);
        HashSet<DiseaseDto> finalResult = new HashSet<>();
        List<RiskFactor> factors = findRiskFactorsFromDiagnoseRequest(plantSicknessRequest.getRiskFactors());
        List<Symptom> symptoms = findSymptomsFromDiagnoseRequest(plantSicknessRequest.getSymptoms());
        PlantType plantType = plantTypeService.findByName(plantSicknessRequest.getPlant());
        for(String res : jessResults){

            String[] resultParts = res.split(":");
            String diseaseTemplateName = res.split(":")[0];
            String diagnoseType = res.split(":")[1];

            Disease disease  = diseaseRepository.findByTemplateNameAndPlantType(diseaseTemplateName, plantType);
            if(disease != null){
                String diagnose = pickDiagnose(disease, diagnoseType);
                List<String> factorNames = factors.stream().map(f-> f.getName()).collect(Collectors.toList());
                List<String> symptomNames = symptoms.stream().map(f-> f.getName()).collect(Collectors.toList());
                DiseaseDto dto = new DiseaseDto(disease.getName(), diagnose,disease.getDiseaseDescription(),disease.getCount());
                finalResult.add(dto);
            }

        }
            DiagnoseDto dto = new DiagnoseDto();
        dto.setDiseases(finalResult);
        dto.setSymptoms(symptoms);
        dto.setRiskFactors(factors);
        return dto;
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

    public byte[] getTempDiseaseImage(String diseaseName) {
        TemporaryDisease disease = temporaryDiseaseRepository.findByDiseaseName(diseaseName);
        byte[] image = fileStorageService.getImageFile(disease.getImageUrl());
        if(image != null) {
            return fileStorageService.getImageFile(disease.getImageUrl());
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
        User user = userService.findByUsername(username);
        Diagnose diagnose = new Diagnose();
        diagnose.setUser(user);
        List<Disease> diseases = dto.getDiseases().stream().map(d -> diseaseRepository.findByName(d.getDiseaseName())).collect(Collectors.toList());
        diagnose.setSymptoms(dto.getSymptoms());
        diagnose.setRiskFactors(dto.getRiskFactors());
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
        return diseases.stream().map( d -> DiseaseDto.convertToDto(d)).filter(d -> d.getCount() > 0 ).collect(Collectors.toList());
    }

    public List<RiskFactor> findRiskFactorsFromDiagnoseRequest(Set<RequestSlotDto> riskFactorsSlotNames){
        return riskFactorsSlotNames.stream().map(rf -> riskFactorRepository.findBySlotName(rf.getSlotName())).collect(Collectors.toList());
    }
    public List<Symptom> findSymptomsFromDiagnoseRequest(Set<RequestSlotDto> symptomSlotNames){
        return symptomSlotNames.stream().map(sym -> symptomRepository.findBySlotName(sym.getSlotName())).collect(Collectors.toList());
    }

}
