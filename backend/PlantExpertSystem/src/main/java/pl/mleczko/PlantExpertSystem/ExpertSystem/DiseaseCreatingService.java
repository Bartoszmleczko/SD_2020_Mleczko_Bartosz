package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Exception.ObjectAlreadyExists;
import pl.mleczko.PlantExpertSystem.Service.*;

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
    private final TemporaryDiseaseService temporaryDiseaseService;


    public DiseaseCreatingService(FileService fileService, DiseaseService diseaseService,
                                  RiskFactorService riskFactorService, SymptomService symptomService,
                                  PlantTypeService plantTypeService, TemporaryDiseaseService temporaryDiseaseService) {
        this.fileService = fileService;
        this.diseaseService = diseaseService;
        this.riskFactorService = riskFactorService;
        this.symptomService = symptomService;
        this.plantTypeService = plantTypeService;
        this.temporaryDiseaseService = temporaryDiseaseService;
    }





    public void writeRiskFactor(RiskFactor form, PlantType plantType) throws IOException {

        RiskFactor factor = riskFactorService.findBySlotNameAndPlantType(form.getSlotName(), plantType);
        if(factor == null){
            File file = fileService.getFile("templates/risk_factors.clp");
//            File file2 = new ClassPathResource("templates/try.txt").getFile();
            String factorName = replaceSlotTemplate(form.getSlotName());
            form.setFactorType(FactorType.OTHER);
            riskFactorService.save(form);
            writeRawTemplateData(file, factorName);
        }else throw new ObjectAlreadyExists(RiskFactor.class.getSimpleName());
    }

    public void writeSymptom(Symptom form, PlantType plantType) throws IOException {
        Symptom symptom = symptomService.findBySlotNameAndPlantType(form.getSlotName(),plantType);
        if(symptom == null){
            File file = fileService.getFile("templates/symptoms.clp");
            String factor = replaceSlotTemplate(form.getSlotName());
            symptomService.save(form);
            writeRawTemplateData(file, factor);
        } else throw new ObjectAlreadyExists(Symptom.class.getSimpleName());


    }

    public Disease writeDiseaseIntoFile(Disease disease) throws IOException {
        File file = fileService.getFile("templates/diseases.clp");

        String jessContent = createDiseaseJessTemplate(disease.getTemplateName());
        Disease dis = diseaseService.save(disease);
        writeDiseaseOrRule(file, jessContent);
        return dis;
    }

    public void writeRawTemplateData(File file, String slot) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        long position = raf.length()-2;
        raf.seek(position);
        raf.write(slot.getBytes());
        raf.write(")".getBytes());
        raf.close();
    }

    public void writeDiseaseOrRule(File file, String slot) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        long position = raf.length();
        raf.seek(position);
        raf.write(slot.getBytes());
        raf.close();
    }

    public void writeRules(List<String> rules, Disease disease) throws IOException {
        File file = fileService.getFile("templates/rules.clp");
        rules.forEach(r -> {
            try {
                writeDiseaseOrRule(file, r);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if(!disease.getPrecautionDiagnose().isEmpty()){
            writeDiseaseOrRule(file, preparePreacutionDiagnose(disease.getPrecautionDiagnose(), disease));
        }
        if(!disease.getInterventionDiagnose().isEmpty()){
            writeDiseaseOrRule(file, prepareInterventionDiagnose(disease.getInterventionDiagnose(), disease));
        }

    }

    private String replaceSlotTemplate(String template){
        String factor = getSlotTemplate();
        factor = factor.replace("{template_name}", template);
        return factor;
    }


    private String getSlotTemplate(){
         return new String(" \n     (slot {template_name}) \n");
    }

    public Disease createNewDisease(TemporaryDisease tDisease) throws IOException {

        TemporaryDisease temporaryDisease = temporaryDiseaseService.findById(tDisease.getId());
        temporaryDisease.setRiskFactors(tDisease.getRiskFactors());
        temporaryDisease = temporaryDiseaseService.save(temporaryDisease);
        System.out.println("check 1");
        Disease tempdisease = diseaseService.findByTemplateNameAndPlantType(temporaryDisease.getDiseaseTemplateName(),temporaryDisease.getPlantType());
        System.out.println("check 2");
        if(tempdisease == null ){
            System.out.println("check 3");
            if(temporaryDisease.getPrecautionDiagnose() == null){
                temporaryDisease.setPrecautionDiagnose("");
            }

            if(temporaryDisease.getInterventionDiagnose() == null){
                temporaryDisease.setInterventionDiagnose("");
            }
            System.out.println("check 4");
            Disease disease = new Disease(temporaryDisease.getDiseaseName(),temporaryDisease.getDiseaseTemplateName(),
                    temporaryDisease.getPrecautionDiagnose(), temporaryDisease.getInterventionDiagnose(),temporaryDisease.getDescription());
            disease.setImageName(temporaryDisease.getImageUrl());
            disease.setPlantType(temporaryDisease.getPlantType());
            disease.setCount(0L);
            System.out.println("check 5");
            Set<TempSymptom> symptoms = temporaryDisease.getSymptoms();
            Set<TempRiskFactor> riskFactors = temporaryDisease.getRiskFactors();

            List<Symptom> symptomsToSave = saveSymptoms(symptoms, disease.getPlantType());
            List<RiskFactor> riskFactorsToSave = saveRiskFactors(riskFactors, disease.getPlantType());
            System.out.println("check6");
            Disease dis = writeDiseaseIntoFile(disease);
            writeRules(temporaryDisease.getJessRules(), dis);
            temporaryDiseaseService.deleteById(temporaryDisease.getId());
            return dis;

        } else{
            System.out.println(tempdisease.getName() + " " + tempdisease.getTemplateName());
            throw new ObjectAlreadyExists("Choroba już istnieje");
        }

    }



    public List<Symptom> saveSymptoms(Set<TempSymptom> tempSymptoms, PlantType plantType) throws IOException {
        List<Symptom> result = new ArrayList<>();
        for( TempSymptom s: tempSymptoms){
            Symptom dbSymptom = symptomService.findBySlotNameAndPlantType(s.getTemplateName(), plantType);
            if(dbSymptom == null){
                Symptom newSymptom = new Symptom();
                newSymptom.setName(s.getName());
                newSymptom.setSlotName(s.getTemplateName());
                newSymptom.setPlantType(plantType);
                writeSymptom(newSymptom, plantType);
                newSymptom = symptomService.save(newSymptom);

                result.add(newSymptom);
            }else{
                result.add(dbSymptom);
            }
        }
        return result;
    }

    public List<RiskFactor> saveRiskFactors(Set<TempRiskFactor> tempRiskFactors, PlantType plantType) throws IOException {
        List<RiskFactor> result = new ArrayList<>();
        for( TempRiskFactor s: tempRiskFactors){
            RiskFactor dbFactor = riskFactorService.findBySlotNameAndPlantType(s.getTemplateName(), plantType);

            if(dbFactor == null){
                RiskFactor newRiskFactor = new RiskFactor();
                newRiskFactor.setName(s.getName());
                newRiskFactor.setSlotName(s.getTemplateName());
                newRiskFactor.setPlantType(plantType);
                newRiskFactor.setFactorType(s.getFactorType());writeRiskFactor(newRiskFactor,plantType);
                newRiskFactor = riskFactorService.save(newRiskFactor);

                result.add(newRiskFactor);
            }else{
                result.add(dbFactor);
            }
        }
        return result;
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

            List<String> dbSlotNames = dbSymptoms.stream().map(s -> s.getSlotName()).collect(Collectors.toList());
            List<String> rawSlotNames = rawSymptoms.stream().map(s -> s.getTemplateName()).collect(Collectors.toList());

            rawSlotNames.removeAll(dbSlotNames);
            result = rawSymptoms.stream().filter(s -> {
                return rawSlotNames.contains(s.getTemplateName());
            }).collect(Collectors.toList());

            return result;
        } return new ArrayList<>();

    }

    public List<TempRiskFactor> getListOfNonExistingRiskFactors(Set<TempRiskFactor> rawRiskFactors, List<RiskFactor> dbFactors){

        if(dbFactors != null){
            List<TempRiskFactor> result = new ArrayList<>();
            List<String> dbSlotNames = dbFactors.stream().map(f -> f.getSlotName()).collect(Collectors.toList());
            List<String> rawSlotNames = rawRiskFactors.stream().map(f -> f.getTemplateName()).collect(Collectors.toList());

            rawSlotNames.removeAll(dbSlotNames);

            result = rawRiskFactors.stream().filter(rf -> {
                return rawSlotNames.contains(rf.getTemplateName());
            }).collect(Collectors.toList());

            return result;
        } return new ArrayList<>();
    }


    public List<RiskFactor> convertListFromTempRiskFactors(List<TempRiskFactor> tempRiskFactors, PlantType plantType){
        return tempRiskFactors.stream().map(trf -> convertFromSingleTempRiskFactor(trf, plantType)).collect(Collectors.toList());
    }

    public RiskFactor convertFromSingleTempRiskFactor(TempRiskFactor rf, PlantType plantType){
        RiskFactor newRiskFactor = new RiskFactor();
        newRiskFactor.setName(rf.getName());
        newRiskFactor.setSlotName(rf.getTemplateName());
        newRiskFactor.setPlantType(plantType);
        return newRiskFactor;
    }

    public List<Symptom> convertListFromTempSymptoms(List<TempSymptom> tempSymptoms, PlantType plantType){
        return tempSymptoms.stream().map( s -> convertSingleTempSymptom(s,plantType)).collect(Collectors.toList());
    }

    public Symptom convertSingleTempSymptom(TempSymptom symptom, PlantType plantType){

        Symptom newSymptom = new Symptom();
        newSymptom.setName(symptom.getName());
        newSymptom.setSlotName(symptom.getTemplateName());
        newSymptom.setPlantType(plantType);

        return newSymptom;

    }

    public String createDiseaseJessTemplate(String templateName){
        StringBuilder content = new StringBuilder("\n( deftemplate ").append(templateName).append("  (slot istnieje))");
        return content.toString();
    }

    public String preparePreacutionDiagnose(String diagnose, Disease disease){


        String objIndex = "str"+String.valueOf(disease.getDiseaseId()+2);
        String leftSideContent = new StringBuilder("\n(").append(disease.getTemplateName()).append(" (istnieje 0.5))").toString();

        StringBuilder content = new StringBuilder(" \n( defrule ").append(disease.getTemplateName()).append("_zapobieg").
                append(leftSideContent).append(" => ( bind ?*").append(objIndex).append("* \"")
                .append(disease.getTemplateName()).append(":zapob\") ").append("( return ?*").append(objIndex+2).append("*))");

                return content.toString();
    }

    public String prepareInterventionDiagnose(String diagnose, Disease disease){
        String objIndex = "str"+String.valueOf(disease.getDiseaseId()+2);
        String leftSideContent = new StringBuilder("\n(").append(disease.getTemplateName()).append(" (istnieje 1))").toString();

        StringBuilder content = new StringBuilder("( defrule ").append(disease.getTemplateName()).append("_interw").
                append(leftSideContent).append(" => ( bind ?*").append(objIndex).append("* \"")
                .append(disease.getTemplateName()).append(":interw\") ").append("( return ?*").append(objIndex).append("*))");

        return content.toString();
    }




}








