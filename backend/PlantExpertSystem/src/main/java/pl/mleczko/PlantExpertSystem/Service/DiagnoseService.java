package pl.mleczko.PlantExpertSystem.Service;

import jess.JessException;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.ExpertSystem.PlantExpertEvalService;
import pl.mleczko.PlantExpertSystem.Model.DiagnoseFormDto;
import pl.mleczko.PlantExpertSystem.Model.PlantSicknessRequest;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;
import pl.mleczko.PlantExpertSystem.Repository.RiskFactorRepository;
import pl.mleczko.PlantExpertSystem.Repository.SymptomRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class DiagnoseService {

    private final RiskFactorRepository riskFactorRepository;
    private final SymptomRepository symptomRepository;
    private final PlantExpertEvalService plantExpertEvalService;
    private final DiseaseRepository diseaseRepository;

    public DiagnoseService(RiskFactorRepository riskFactorRepository, SymptomRepository symptomRepository, PlantExpertEvalService plantExpertEvalService, DiseaseRepository diseaseRepository) {
        this.riskFactorRepository = riskFactorRepository;
        this.symptomRepository = symptomRepository;
        this.plantExpertEvalService = plantExpertEvalService;
        this.diseaseRepository = diseaseRepository;
    }

    @Transactional
    public HashMap<FactorType, HashSet<RiskFactor>> getAllFactorsByTypeAndPlantType(PlantType plantType){
        HashMap<FactorType, HashSet<RiskFactor>> result = new HashMap<>();

        for(FactorType type : FactorType.values()){
            HashSet<RiskFactor> factors = new HashSet<>(riskFactorRepository.findAllByPlantTypeAndFactorType(plantType,type));
            result.put(type,factors);
        }

        return result;
    }

    @Transactional
    public HashSet<Symptom> getAllSymptomsByPlantType(PlantType plantType){
        return new HashSet<>(symptomRepository.findAllByPlantType(plantType));
    }

    public DiagnoseFormDto aggregateRiskFactorsAndSymptomsForForm(PlantType plantType){
        DiagnoseFormDto formDto = new DiagnoseFormDto();

        formDto.setRiskFactors(getAllFactorsByTypeAndPlantType(plantType));
        formDto.setSymptoms(getAllSymptomsByPlantType(plantType));

        return formDto;
    }

    @Transactional
    public Disease findDiseaseByTemplateName(String templateName){
        return diseaseRepository.findByTemplateName(templateName);
    }

    public HashSet<DiseaseDto> diagnose(PlantSicknessRequest plantSicknessRequest) throws JessException {
        ArrayList<String> jessResults = (ArrayList<String>) plantExpertEvalService.provideFact(plantSicknessRequest);
        HashSet<DiseaseDto> finalResult = new HashSet<>();

        for(String res : jessResults){

            String[] resultParts = res.split(":");
            String diseaseTemplateName = res.split(":")[0];
            String diagnoseType = res.split(":")[1];



            Disease disease  = findDiseaseByTemplateName(diseaseTemplateName);

            String diagnose = pickDiagnose(disease, diagnoseType);
            DiseaseDto dto = new DiseaseDto(disease.getName(), diagnose);
            finalResult.add(dto);
        }
        return finalResult;
    }

    private String pickDiagnose(Disease disease, String diagnoseType) {

        if(diagnoseType.equals("interw"))
            return disease.getInterventionDiagnose();
        else return disease.getPrecautionDiagnose();
    }




}
