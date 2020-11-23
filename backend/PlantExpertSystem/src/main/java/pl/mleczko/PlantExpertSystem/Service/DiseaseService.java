package pl.mleczko.PlantExpertSystem.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Exception.DiseaseCreationException;
import pl.mleczko.PlantExpertSystem.Exception.FileValidationException;
import pl.mleczko.PlantExpertSystem.Exception.NotFoundException;
import pl.mleczko.PlantExpertSystem.Model.*;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;
import pl.mleczko.PlantExpertSystem.Validator.NewDiseaseFileValidator;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final FileStorageService fileStorageService;
    private final NewDiseaseFileValidator newDiseaseFileValidation;
    private final PlantTypeService plantTypeService;
    private final TemporaryDiseaseService temporaryDiseaseService;
    private final ObjectMapper objectMapper;

    public DiseaseService(DiseaseRepository diseaseRepository, FileStorageService fileStorageService, NewDiseaseFileValidator newDiseaseFileValidation, PlantTypeService plantTypeService, TemporaryDiseaseService temporaryDiseaseService, ObjectMapper objectMapper) {
        this.diseaseRepository = diseaseRepository;
        this.fileStorageService = fileStorageService;
        this.newDiseaseFileValidation = newDiseaseFileValidation;
        this.plantTypeService = plantTypeService;
        this.temporaryDiseaseService = temporaryDiseaseService;
        this.objectMapper = objectMapper;
    }

//    @Transactional
//    public TemporaryDisease addNewDisease(MultipartFile file, String plantType, MultipartFile image) throws IOException {
//
//        if(!FilenameUtils.isExtension(file.getOriginalFilename(), "txt")){
//            throw new FileValidationException("Zły format pliku tekstowego");
//        }
//
//        NewDiseaseForm  newForm = parseTxtFileForNewDisease(file);
//
//        PlantType dbPlantType = plantTypeService.findByName(plantType);
//
//        TemporaryDisease tempDisease = new TemporaryDisease();
//
//        tempDisease.setDiseaseName(newForm.getDiseaseName());
//        tempDisease.setDiseaseTemplateName(newForm.getDiseaseTemplateName());
//        tempDisease.setRiskFactors(createTempRiskFactorSet(newForm.getRiskFactors()));
//        tempDisease.setSymptoms(createTempSymptomSet(newForm.getSymptoms()));
//        tempDisease.setDescription(newForm.getDescription());
//        tempDisease.setInterventionDiagnose(newForm.getInterventionDiagnose());
//        tempDisease.setPrecautionDiagnose(newForm.getPrecautionDiagnose());
//        tempDisease.setRules(Arrays.asList(newForm.getRules()));
//        tempDisease.setJessRules(generateJessRule(newForm));
//        tempDisease.setPlantType(dbPlantType);
//        tempDisease.setRequestDate(LocalDateTime.now());
//
//        boolean correctImageFormat = FilenameUtils.isExtension(image.getOriginalFilename(), Arrays.asList("jpg", "jpeg", "png"));
//        if(!correctImageFormat)
//            throw new FileValidationException("Zły format zdjęcia");
//        fileStorageService.store(image,newForm.getDiseaseTemplateName());
//        tempDisease.setImageUrl(newForm.getDiseaseTemplateName()+"."+FilenameUtils.getExtension(image.getOriginalFilename()));
//
//
//        return temporaryDiseaseService.save(tempDisease);
//    }


    @Transactional
    public TemporaryDisease addNewDisease(TemporaryDiseaseTemplateForm form) throws JsonProcessingException {

        String diseaseTemplateName = convertToLowerCaseWithUnderscores(form.getDiseaseName());
        TemporaryDisease dbDisease = temporaryDiseaseService.findByTemplateName(diseaseTemplateName);
        PlantType plantType = plantTypeService.findByName(form.getPlantType());
        System.out.println(plantType.getName());
        if(dbDisease == null){

            TemporaryDisease newTempDisease = new TemporaryDisease();
            newTempDisease.setPlantType(plantType);
            newTempDisease.setDiseaseName(form.getDiseaseName().trim());
            newTempDisease.setDescription(form.getDiseaseDescription().trim());
            newTempDisease.setDiseaseTemplateName(diseaseTemplateName);
            if(form.getPrecautionDiagnose() == null)
            newTempDisease.setPrecautionDiagnose("");
            newTempDisease.setInterventionDiagnose(form.getInterventionDiagnose());
            List<String> rulesList = Arrays.asList(parseJsonSymptomAndRiskFactors(form.getRules()));

            newTempDisease.setRules(rulesList);
            String[] symptomArr = parseJsonSymptomAndRiskFactors(form.getSymptoms());

            String[] riskFactorArr = parseJsonSymptomAndRiskFactors(form.getRiskFactors());


            Set<TempSymptom> tempSymptoms = createTempSymptomSet(getTemplateData(symptomArr));
            newTempDisease.setSymptoms(tempSymptoms);
            Set<TempRiskFactor> tempRiskFactors = createTempRiskFactorSet(getTemplateData(riskFactorArr));
            newTempDisease.setRiskFactors(tempRiskFactors);


            List<RuleForm> forms = parseTextRulesToObjects(newTempDisease.getRules().toArray(new String[]{}), getTemplateData(symptomArr)
                    , getTemplateData(riskFactorArr));
            newTempDisease.setJessRules(generateJessRule(forms, newTempDisease.getDiseaseTemplateName()));
            boolean correctImageFormat = FilenameUtils.isExtension(form.getImage().getOriginalFilename(), Arrays.asList("jpg", "jpeg", "png"));
            if(!correctImageFormat)
                throw new FileValidationException("Zły format zdjęcia.");
            fileStorageService.store(form.getImage(),newTempDisease.getDiseaseTemplateName());
            newTempDisease.setImageUrl(newTempDisease.getDiseaseTemplateName()+"."+FilenameUtils.getExtension(form.getImage().getOriginalFilename()));
            return temporaryDiseaseService.save(newTempDisease);
        } else{
            throw new DiseaseCreationException("Choroba juz występuje.");
        }

    }


    @Transactional
    public List<Disease> findAllByName(List<String> names){

        return names.stream().map(n -> diseaseRepository.findByName(n)).collect(Collectors.toList());
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
                    dtos.add(new DiseaseDto(disease1.getName(), null, disease1.getDiseaseDescription(), disease1.getCount()));
            });
            return new PageImpl<>(dtos,pageable, allDiseasesSize);
    }

    private String getTextFromFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        return content;
    }

//    public String[] splitStringToSegments(String content, String separator){
//        if(content == null || content.trim().isEmpty()){
//            throw new FileValidationException("File is empty");
//        } else {
//            content = content.trim();
//            content = content.replaceAll("[\\\r\\\n]+","");
//            String[] result = Arrays.stream(content.split(separator)).map(String::trim).toArray(String[]::new);
//
//            if(result[0].trim().isEmpty()){
//                String[] result2 = new String[result.length-1];
//                for(int i = 0; i<result.length-1; i++){
//                    result2[i] = result[i+1];
//                }
//                return result2;
//            }
//            return result;
//        }
//
//    }

//    public NewDiseaseForm parseTxtFileForNewDisease(MultipartFile file) throws IOException {
//        File file  = new File("src/main/resources/templates/jesstest.txt");
//
//        String content = getTextFromFile(file);
//        String[] segments = splitStringToSegments(content, ";");
//
//        if(newDiseaseFileValidation.isSegmentNumberRight(segments.length) && newDiseaseFileValidation.checkSegmentHeadlines(segments)){
//            Map<String, String[]> contentMap = getSegmentContent(segments);
//            NewDiseaseForm diseaseForm = prepareTemplate(contentMap);
//            return diseaseForm;
//        } else{
//            throw new FileValidationException("Zły format pliku");
//        }
//    }

//    public Map<String, String[]> getSegmentContent(String[] segments){
//        if(segments != null){
//            Map<String, String[]> contents = new HashMap<>();
//            for( int i = 0; i<segments.length; i++){
//                segments[i]= segments[i].trim();
//                segments[i] = segments[i].replaceAll("::", ":");
//                String[] temp = segments[i].split(":");
//                if(temp.length > 1){
//                    //trim all elements of each segment and remove blank spaces
//                    String[] details =Arrays.stream(temp[1].trim().split(",")).map(String::trim).toArray(String[]::new);
//                    List<String> detList = new ArrayList<String>(Arrays.asList(details));
//                    detList.removeAll(Arrays.asList(""));
//                    details = new String[detList.size()];
//                    details = detList.toArray(details);
//                    contents.put(temp[0], details);
//                }
//                else contents.put(temp[0], null);
//            }
//            return contents;
//        } else throw new FileValidationException("Plik jest pusty");
//
//    }

//    public NewDiseaseForm prepareTemplate(Map<String, String[]> content){
//
//        NewDiseaseForm newDiseaseForm = new NewDiseaseForm();
//        String diseaseName;
//        String[] disease = content.get("CHOROBA");
//        diseaseName = convertToLowerCaseWithUnderscores(disease[0]);
//        newDiseaseForm.setDiseaseName(disease[0].trim());
//        newDiseaseForm.setDiseaseTemplateName(convertToLowerCaseWithUnderscores(disease[0]));
//
//        String[] symptomsFromFile = content.get("SYMPTOMY");
//        Set<SimpleTemplateForm> symptomsForms = getTemplateData(symptomsFromFile);
//        newDiseaseForm.setSymptoms(symptomsForms);
//        String[] riskFactorsFromFile = content.get("CZYNNIKI RYZYKA");
//        Set<SimpleTemplateForm> riskFactors = getTemplateData(riskFactorsFromFile);
//        newDiseaseForm.setRiskFactors(riskFactors);
//
//        if(content.get("KOMUNIKAT ZAPOBIEGAWCZY") != null){
//            if(!content.get("KOMUNIKAT ZAPOBIEGAWCZY")[0].trim().isEmpty())
//            newDiseaseForm.setPrecautionDiagnose(content.get("KOMUNIKAT ZAPOBIEGAWCZY")[0]);
//        }
//
//
//        if(content.get("KOMUNIKAT INTERWENCYJNY") != null ){
//            if(!content.get("KOMUNIKAT INTERWENCYJNY")[0].trim().isEmpty())
//            newDiseaseForm.setInterventionDiagnose(content.get("KOMUNIKAT INTERWENCYJNY")[0]);
//        }
//
//
//        String[] rules = content.get("REGUŁY WNIOSKUJĄCE");
//        newDiseaseForm.setRules(rules);
//
//        if(content.get("OPIS CHOROBY")[0] != null)
//            newDiseaseForm.setDescription(content.get("OPIS CHOROBY")[0]);
//
//        List<RuleForm> forms = parseTextRulesToObjects(rules, newDiseaseForm.getSymptoms(), newDiseaseForm.getRiskFactors());
//        newDiseaseForm.setRuleForms(forms);
//
//        return newDiseaseForm;
//
//
//    }






    public Set<SimpleTemplateForm> getTemplateData(String[] templates){
        if(templates != null){
            if(templates.length>0){
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
                name = name.replaceAll("  ", " ");
                name = name.replaceAll(" ", "_");
                return name;
            }
        }  throw new FileValidationException("Błąd walidacji2");

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
                            int index = Integer.parseInt(separateResult[1]);

                            ruleForm.setResult(index);
                            if(ruleParts[i].trim().charAt(0) == 'S'){

                                SimpleTemplateForm form = findTemplateForm(symptoms, Integer.parseInt(String.valueOf(ruleParts[i].charAt(1))) );
                                symptomRules.add(form);
                            }
                            if(ruleParts[i].trim().charAt(0) == 'R'){
                                SimpleTemplateForm form = findTemplateForm(riskFactors,Integer.parseInt(String.valueOf(ruleParts[i].charAt(1))));
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
        } throw new FileValidationException("Błąd walidacji reguł");

    }

public SimpleTemplateForm findTemplateForm(Set<SimpleTemplateForm> forms, int index){
        forms.forEach(f -> System.out.println(f.getId() + f.getTemplateName()));
    System.out.println(index);
        if(forms != null){
            SimpleTemplateForm form = new SimpleTemplateForm();
            for(SimpleTemplateForm f : forms){
                if(f.getId() == index){
                    form = f;
                    return f;
                }
            }
        } throw new FileValidationException("Błąd walidacji1");
}


    public List<String> generateJessRule(List<RuleForm> forms, String templateName){



//        (defrule suchaZgniliznaKapustnych01
//                (risk_factors  (czesta_uprawa_rzepaku 1) (uprawa_rzepaku_okolica 1) (dluga_ciepla_jesien 1) )
//     => (assert (sucha_zgnilizna_kapustnych (istnieje 1) ))
// )

        List<String> result = new ArrayList<>();


        for(int i = 0; i<forms.size(); i++){
            String res = "1";
            if(forms.get(i).getResult() == 0){
                res = "0.5";
            }
            String header = "\n(defrule " + templateName.trim();
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

            header = header + "=> ( assert ( " + templateName.trim() + "(istnieje " + res + ")))";

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
        return  new TempRiskFactor(null, simpleForm.getName(), simpleForm.getTemplateName(),FactorType.OTHER);
    }

    public TempSymptom createTempSymptom( SimpleTemplateForm simpleForm){
        return new TempSymptom(null, simpleForm.getName(), simpleForm.getTemplateName());
    }

    public String[] parseJsonSymptomAndRiskFactors(String template) throws JsonProcessingException {

        FormDto[] fdArr = objectMapper.readValue(template, FormDto[].class);
        String[] tempArr;
       tempArr = Arrays.asList(fdArr).stream().map(f ->{
            return f.getTemplateName();}
            ).collect(Collectors.toList()).toArray(new String[]{});
        return tempArr;
    }



}