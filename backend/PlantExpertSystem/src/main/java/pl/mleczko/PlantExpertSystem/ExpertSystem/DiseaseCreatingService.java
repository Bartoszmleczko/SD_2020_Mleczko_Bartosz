package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Exception.FileValidationException;
import pl.mleczko.PlantExpertSystem.Exception.ObjectAlreadyExists;
import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;
import pl.mleczko.PlantExpertSystem.Model.RuleForm;
import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;
import pl.mleczko.PlantExpertSystem.Service.DiseaseService;
import pl.mleczko.PlantExpertSystem.Service.PlantTypeService;
import pl.mleczko.PlantExpertSystem.Service.RiskFactorService;
import pl.mleczko.PlantExpertSystem.Service.SymptomService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiseaseCreatingService {

    private final FileService fileService;
    private final DiseaseService diseaseService;
    private final RiskFactorService riskFactorService;
    private final SymptomService symptomService;
    private final PlantTypeService plantTypeService;


    public DiseaseCreatingService(FileService fileService, DiseaseService diseaseService, RiskFactorService riskFactorService, SymptomService symptomService, PlantTypeService plantTypeService) {
        this.fileService = fileService;
        this.diseaseService = diseaseService;
        this.riskFactorService = riskFactorService;
        this.symptomService = symptomService;
        this.plantTypeService = plantTypeService;
    }





    public void writeRiskFactor(SimpleTemplateForm form) throws IOException {
        form.setTemplateName("choroba");
        RiskFactor factor = riskFactorService.findBySlotName(form.getTemplateName());

        if(factor == null){
            File file = fileService.getFile("templates/risk_factors.clp");
            File file2 = new ClassPathResource("templates/try.txt").getFile();
            String factorName = replaceSlotTemplate(form.getTemplateName());
            writeRawTemplateData(file2, factorName);
        }else throw new ObjectAlreadyExists(RiskFactor.class.getSimpleName());
    }

    public void writeSymptom(SimpleTemplateForm form) throws IOException {
        Symptom symptom = symptomService.findBySlotName(form.getTemplateName());

        if(symptom == null){
            File file = fileService.getFile("templates/symptoms.clp");
            String factor = replaceSlotTemplate(form.getTemplateName());
            writeRawTemplateData(file, factor);
        } else throw new ObjectAlreadyExists(Symptom.class.getSimpleName());


    }

    public void writeRawTemplateData(File file, String slot) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        long position = raf.length()-2;
        raf.seek(position);
        raf.write(slot.getBytes());
        raf.write(")".getBytes());
        raf.close();
    }

    private String replaceSlotTemplate(String template){
        String factor = getSlotTemplate();
        factor = factor.replace("{template_name}", template);
        return factor;
    }


    private String getSlotTemplate(){
         return new String(" \n     (slot {template_name}) \n");
    }

    public void createNewDisease(TemporaryDisease temporaryDisease) throws IOException {

        Disease tempdisease = diseaseService.findByNameOrTemplateNameAndPlantType(temporaryDisease.getDiseaseName(),
                temporaryDisease.getDiseaseTemplateName(),temporaryDisease.getPlantType());

        if(tempdisease == null ){

            Disease disease = new Disease(temporaryDisease.getDiseaseName(),temporaryDisease.getDiseaseTemplateName(),
                    tempdisease.getPrecautionDiagnose(), tempdisease.getInterventionDiagnose(),temporaryDisease.getDescription());

            Set<TempSymptom> symptoms = temporaryDisease.getSymptoms();
            Set<TempRiskFactor> riskFactors = temporaryDisease.getRiskFactors();

            List<Symptom> dbSymptoms = getExistingSymptomsFromDatabase(symptoms, temporaryDisease.getPlantType());
            List<RiskFactor> dbRiskFactors = getExistingRiskFactorsFromDatabase(riskFactors, temporaryDisease.getPlantType());

            List<TempSymptom> notExistingSymptoms = getListOfNonExistingSymptoms(symptoms, dbSymptoms);
            List<TempRiskFactor> notExistingRiskFactors = getListOfNonExistingRiskFactors(riskFactors, dbRiskFactors);




        } else throw new FileValidationException("Choroba już istnieje");

    }


    public List<Symptom> getExistingSymptomsFromDatabase(Set<TempSymptom> symptoms, PlantType type){
        List<String> symptomSlotNames = symptoms.stream().map(s -> s.getTemplateName()).collect(Collectors.toList());

        List<Symptom> dbSymptoms = symptomService.findAllByNameInOrSlotNameInAndPlantType(symptomSlotNames, type);
        return dbSymptoms;
    }

    public List<RiskFactor> getExistingRiskFactorsFromDatabase(Set<TempRiskFactor> riskFactors, PlantType type){
        List<String> rfSlotNames = riskFactors.stream().map(r -> r.getTemplateName()).collect(Collectors.toList());

        List<RiskFactor> dbFactors = riskFactorService.findAllByNameInOrSlotNameInAndPlantType( rfSlotNames, type);
        return dbFactors;
    }

    public List<TempSymptom> getListOfNonExistingSymptoms(Set<TempSymptom> rawSymptoms, List<Symptom> dbSymptoms ){
        if(dbSymptoms != null){
            List<TempSymptom> result = new ArrayList<>();
            for(TempSymptom f: rawSymptoms){
                dbSymptoms.forEach(dbs -> {
                    if(f.getTemplateName() == dbs.getSlotName() && f.getName() == dbs.getName()){
                        return;
                    }
                    result.add(f);
                });
            }
            return result;
        } else return rawSymptoms.stream().collect(Collectors.toList());

    }

    public List<TempRiskFactor> getListOfNonExistingRiskFactors(Set<TempRiskFactor> rawRiskFactors, List<RiskFactor> dbFactors){

        if(dbFactors != null){
            List<TempRiskFactor> result = new ArrayList<>();
            List<String> dbSlotNames = dbFactors.stream().map(f -> f.getSlotName()).collect(Collectors.toList());
            List<String> rawSlotNames = rawRiskFactors.stream().map(f -> f.getTemplateName()).collect(Collectors.toList());

            rawSlotNames.removeAll(dbSlotNames);

            return result;
        }
    }


}
