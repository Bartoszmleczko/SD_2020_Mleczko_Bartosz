package pl.mleczko.PlantExpertSystem.Service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Exception.FileValidationException;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Model.DiseaseDto;
import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;
import pl.mleczko.PlantExpertSystem.Model.RuleForm;
import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;
import pl.mleczko.PlantExpertSystem.Validator.NewDiseaseFileValidator;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final FileStorageService fileStorageService;
    private final NewDiseaseFileValidator newDiseaseFileValidation;
    private final PlantTypeService plantTypeService;
    private final TemporaryDiseaseService temporaryDiseaseService;

    public DiseaseService(DiseaseRepository diseaseRepository, FileStorageService fileStorageService, NewDiseaseFileValidator newDiseaseFileValidation, PlantTypeService plantTypeService, TemporaryDiseaseService temporaryDiseaseService) {
        this.diseaseRepository = diseaseRepository;
        this.fileStorageService = fileStorageService;
        this.newDiseaseFileValidation = newDiseaseFileValidation;
        this.plantTypeService = plantTypeService;
        this.temporaryDiseaseService = temporaryDiseaseService;
    }

    @Transactional
    public TemporaryDisease addNewDisease(MultipartFile file, String plantType) throws IOException {

        NewDiseaseForm  newForm = parseTxtFileForNewDisease();

        PlantType dbPlantType = plantTypeService.findByName(plantType);

        TemporaryDisease tempDisease = new TemporaryDisease();

        tempDisease.setDiseaseName(newForm.getDiseaseName());
        tempDisease.setDiseaseTemplateName(newForm.getDiseaseTemplateName());
        tempDisease.setRiskFactors(createTempRiskFactorSet(newForm.getRiskFactors()));
        tempDisease.setSymptoms(createTempSymptomSet(newForm.getSymptoms()));
        tempDisease.setDescription(newForm.getDescription());
        tempDisease.setInterventionDiagnose(newForm.getInterventionDiagnose());
        tempDisease.setPrecautionDiagnose(newForm.getPrecautionDiagnose());
        tempDisease.setRules(Arrays.asList(newForm.getRules()));
        tempDisease.setJessRules(generateJessRule(newForm));
        tempDisease.setPlantType(dbPlantType);
        tempDisease.setRequestDate(LocalDateTime.now());

        return temporaryDiseaseService.save(tempDisease);
    }

    @Transactional
    public Disease save(Disease disease){
        return diseaseRepository.save(disease);
    }

    @Transactional
    public Disease findById(Long id){
        return diseaseRepository.findById(id).orElseThrow(() -> new NotFoundException(Disease.class.getSimpleName()));
    }

    @Transactional
    public Disease findByTemplateName(String templateName){
        return diseaseRepository.findByTemplateName(templateName);
    }

    public Disease findByNameOrTemplateNameAndPlantType(String name, String templateName, PlantType plantType){
        return diseaseRepository.findByNameOrTemplateNameAndPlantType(name, templateName, plantType);
    }

    @Transactional
    public Page<DiseaseDto> findAll(Pageable pageable){

            List<DiseaseDto> dtos = new ArrayList<>();
            long allDiseasesSize = diseaseRepository.findAll().size();
            Page<Disease> disease = diseaseRepository.findAll(pageable);
            disease.forEach(disease1 -> {
                try {
                    String diseaseDescription = fileStorageService.getDescriptionTxtFile(disease1.getTemplateName()+".txt");
                    dtos.add(new DiseaseDto(disease1.getName(), null, diseaseDescription ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return new PageImpl<>(dtos,pageable, allDiseasesSize);
    }

    private String getTextFromFile(File file) throws IOException {
        String content = FileUtils.readFileToString(file, "UTF-8");
        return content;
    }

    public String[] splitStringToSegments(String content, String separator){
        if(content == null || content.trim().isEmpty()){
            throw new FileValidationException("File is empty");
        } else {
            content = content.trim();
            content = content.replaceAll("[\\\r\\\n]+","");
            String[] result = Arrays.stream(content.split(separator)).map(String::trim).toArray(String[]::new);

            if(result[0].trim().isEmpty()){
                String[] result2 = new String[result.length-1];
                for(int i = 0; i<result.length-1; i++){
                    result2[i] = result[i+1];
                }
                return result2;
            }
            return result;
        }

    }

    public NewDiseaseForm parseTxtFileForNewDisease() throws IOException {
        File file  = new File("src/main/resources/templates/jesstest.txt");

        String content = getTextFromFile(file);
        String[] segments = splitStringToSegments(content, ";");

        if(newDiseaseFileValidation.isSegmentNumberRight(segments.length) && newDiseaseFileValidation.checkSegmentHeadlines(segments)){
            Map<String, String[]> contentMap = getSegmentContent(segments);
            NewDiseaseForm diseaseForm = prepareTemplate(contentMap);
            return diseaseForm;
        } else{
            throw new FileValidationException("Zły format pliku");
        }
    }

    public Map<String, String[]> getSegmentContent(String[] segments){
        if(segments != null){
            Map<String, String[]> contents = new HashMap<>();
            for( int i = 0; i<segments.length; i++){
                segments[i]= segments[i].trim();
                segments[i] = segments[i].replaceAll("::", ":");
                String[] temp = segments[i].split(":");
                if(temp.length > 1){
                    //trim all elements of each segment and remove blank spaces
                    String[] details =Arrays.stream(temp[1].trim().split(",")).map(String::trim).toArray(String[]::new);
                    List<String> detList = new ArrayList<String>(Arrays.asList(details));
                    detList.removeAll(Arrays.asList(""));
                    details = new String[detList.size()];
                    details = detList.toArray(details);
                    contents.put(temp[0], details);
                }
                else contents.put(temp[0], null);
            }
            return contents;
        } else throw new FileValidationException("Plik jest pusty");

    }

    public NewDiseaseForm prepareTemplate(Map<String, String[]> content){

        NewDiseaseForm newDiseaseForm = new NewDiseaseForm();
        String diseaseName;
        String[] disease = content.get("CHOROBA");
        diseaseName = convertToLowerCaseWithUnderscores(disease[0]);
        newDiseaseForm.setDiseaseName(disease[0].trim());
        newDiseaseForm.setDiseaseTemplateName(convertToLowerCaseWithUnderscores(disease[0]));

        String[] symptomsFromFile = content.get("SYMPTOMY");
        Set<SimpleTemplateForm> symptomsForms = getTemplateData(symptomsFromFile);
        newDiseaseForm.setSymptoms(symptomsForms);
        String[] riskFactorsFromFile = content.get("CZYNNIKI RYZYKA");
        Set<SimpleTemplateForm> riskFactors = getTemplateData(riskFactorsFromFile);
        newDiseaseForm.setRiskFactors(riskFactors);

        if(content.get("KOMUNIKAT ZAPOBIEGAWCZY") != null){
            if(!content.get("KOMUNIKAT ZAPOBIEGAWCZY")[0].trim().isEmpty())
            newDiseaseForm.setPrecautionDiagnose(content.get("KOMUNIKAT ZAPOBIEGAWCZY")[0]);
        }


        if(content.get("KOMUNIKAT INTERWENCYJNY") != null ){
            if(!content.get("KOMUNIKAT INTERWENCYJNY")[0].trim().isEmpty())
            newDiseaseForm.setInterventionDiagnose(content.get("KOMUNIKAT INTERWENCYJNY")[0]);
        }


        String[] rules = content.get("REGUŁY WNIOSKUJĄCE");
        newDiseaseForm.setRules(rules);

        if(content.get("OPIS CHOROBY")[0] != null)
            newDiseaseForm.setDescription(content.get("OPIS CHOROBY")[0]);

        List<RuleForm> forms = parseTextRulesToObjects(rules, newDiseaseForm.getSymptoms(), newDiseaseForm.getRiskFactors());
        newDiseaseForm.setRuleForms(forms);

        return newDiseaseForm;


    }






    public Set<SimpleTemplateForm> getTemplateData(String[] templates){
        if(templates != null){
            if(templates.length!=0){
                Set<SimpleTemplateForm> forms = new HashSet<>();
                for(int i = 0; i < templates.length; i++){
                    SimpleTemplateForm tempForm = new SimpleTemplateForm();
                    templates[i] = templates[i].trim();
                    if(!templates[i].isEmpty()){
                        tempForm.setId(i);
                        tempForm.setName(templates[i]);
                        tempForm.setTemplateName(convertToLowerCaseWithUnderscores(templates[i]));
                        forms.add(tempForm);
                    }
                }
                return forms;
            }
        } throw new FileValidationException("Błąd walidacji pliku");
    }

    public String convertToLowerCaseWithUnderscores(String name){
        if(name != null){
            if(!name.trim().isEmpty()) {
                name = StringUtils.stripAccents(name);
                name = name.toLowerCase();
                name = name.trim();
                name = name.replaceAll(" ", "_");
                return name;
            }
        }  throw new FileValidationException("Błąd walidacji");

    }


    public List<RuleForm> parseTextRulesToObjects(String[] rawRules, Set<SimpleTemplateForm> symptoms, Set<SimpleTemplateForm> riskFactors) {
        if (rawRules != null) {
            if (rawRules.length > 0) {
                List<RuleForm> result = new ArrayList<>();
                for (int i = 0; i < rawRules.length; i++) {
                    RuleForm form = parseSingleRule(rawRules[i], symptoms, riskFactors);
                    result.add(form);
                }
                return result;
            }
        } throw new FileValidationException("Brak reguł wnioskujących");
    }


    public RuleForm parseSingleRule(String rawRule, Set<SimpleTemplateForm> symptoms, Set<SimpleTemplateForm> riskFactors){

        if(rawRule != null && symptoms != null && riskFactors != null){
            if(!rawRule.isEmpty()){
                String[] separateResult = rawRule.split("=");
                separateResult[0] = separateResult[0].trim();
                separateResult[1] = separateResult[1].trim();
                if(separateResult[1].equals("0") || separateResult[1].equals("1")) {
                    String[] ruleParts = separateResult[0].split("[+]");
                    for(int i = 0 ; i < ruleParts.length; i++){
                        ruleParts[i] = ruleParts[i].trim();
                    }
                    List<String> detList = new ArrayList<String>(Arrays.asList(ruleParts));
                    detList.removeAll(Arrays.asList(""));
                    ruleParts = new String[detList.size()];
                    ruleParts = detList.toArray(ruleParts);
                    RuleForm ruleForm = new RuleForm();
                    List<SimpleTemplateForm> symptomRules = new ArrayList<>();
                    List<SimpleTemplateForm> riskFactorRules = new ArrayList<>();
                    for(int i = 0; i< ruleParts.length; i++){
                        if(StringUtils.isNumeric(String.valueOf(ruleParts[i].charAt(1)))){
                            int index = Integer.parseInt(String.valueOf(ruleParts[i].charAt(1)));

                            ruleForm.setResult(index);
                            if(ruleParts[i].trim().charAt(0) == 'S'){
                                SimpleTemplateForm form = findTemplateForm(symptoms,index );
                                symptomRules.add(form);
                            }
                            if(ruleParts[i].trim().charAt(0) == 'R'){
                                SimpleTemplateForm form = findTemplateForm(riskFactors, index);
                                riskFactorRules.add(form);
                            }
                        }
                    }
                    if(riskFactorRules != null && symptomRules != null){
                        ruleForm.setSymptomParts(symptomRules);
                        ruleForm.setRiskFactorParts(riskFactorRules);
                        return ruleForm;
                    }

                }
            }
        } throw new FileValidationException("Błąd walidacji");

    }

public SimpleTemplateForm findTemplateForm(Set<SimpleTemplateForm> forms, int index){
        if(forms != null){
            SimpleTemplateForm form = new SimpleTemplateForm();
            for(SimpleTemplateForm f : forms){
                if(f.getId() == index){
                    form = f;
                    return f;
                }
            }
        } throw new FileValidationException("Błąd walidacji");
}


    public List<String> generateJessRule(NewDiseaseForm diseaseForm){

        List<RuleForm> forms = diseaseForm.getRuleForms();

//        (defrule suchaZgniliznaKapustnych01
//                (risk_factors  (czesta_uprawa_rzepaku 1) (uprawa_rzepaku_okolica 1) (dluga_ciepla_jesien 1) )
//     => (assert (sucha_zgnilizna_kapustnych (istnieje 1) ))
// )

        List<String> result = new ArrayList<>();


        for(int i = 0; i<forms.size(); i++){
            String header = "(defrule " + diseaseForm.getDiseaseTemplateName();
            header = header + String.valueOf(i);
            if( !forms.get(i).getRiskFactorParts().isEmpty()){
                String riskFactorString = " (risk_factors ";
                for(SimpleTemplateForm f : forms.get(i).getRiskFactorParts()){
                    riskFactorString = riskFactorString + " ( "+f.getTemplateName() + " 1 )";
                }
                riskFactorString = riskFactorString + " )";
                header = header + riskFactorString;
            }

            if( !forms.get(i).getSymptomParts().isEmpty()){
                String symptomString = " ( symptoms ";
                for(SimpleTemplateForm f : forms.get(i).getSymptomParts()){
                    symptomString = symptomString + " ( "+f.getTemplateName() + " 1 )";
                }
                symptomString = symptomString + " ) ";
                header = header + symptomString;
            }

            header = header + "=> ( assert ( " + diseaseForm.getDiseaseTemplateName() + "(istnieje 1)))";

            header = header + " )";
            result.add(header);
        }
        return result;
    }

    public Set<TempRiskFactor> createTempRiskFactorSet(Set<SimpleTemplateForm> forms){
        return  forms.stream().map(f -> createTempRiskFactor(f)).collect(Collectors.toSet());
    }

    public Set<TempSymptom> createTempSymptomSet( Set<SimpleTemplateForm> forms){
        return forms.stream().map(f -> createTempSymptom(f)).collect(Collectors.toSet());
    }

    public TempRiskFactor createTempRiskFactor(SimpleTemplateForm simpleForm){
        return  new TempRiskFactor(null, simpleForm.getName(), simpleForm.getTemplateName());
    }

    public TempSymptom createTempSymptom( SimpleTemplateForm simpleForm){
        return new TempSymptom(null, simpleForm.getName(), simpleForm.getTemplateName());
    }

}