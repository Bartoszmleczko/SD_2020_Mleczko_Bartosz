package pl.mleczko.PlantExpertSystem.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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



    @Transactional
    public TemporaryDisease addNewDisease(TemporaryDiseaseTemplateForm form) throws JsonProcessingException {

        String diseaseTemplateName = convertToLowerCaseWithUnderscores(form.getDiseaseName());
        TemporaryDisease dbDisease = temporaryDiseaseService.findByTemplateName(diseaseTemplateName);
        PlantType plantType = plantTypeService.findByName(form.getPlantType());

        if(dbDisease == null){

            TemporaryDisease newTempDisease = new TemporaryDisease();
            newTempDisease.setPlantType(plantType);
            newTempDisease.setDiseaseName(form.getDiseaseName().trim());
            newTempDisease.setDescription(form.getDiseaseDescription().trim());
            newTempDisease.setDiseaseTemplateName(diseaseTemplateName);
            if(form.getPrecautionDiagnose() == null)
            newTempDisease.setPrecautionDiagnose("");
            newTempDisease.setInterventionDiagnose(form.getInterventionDiagnose());
            List<RuleTemplate> rulesList = Arrays.asList(parseRuleString(form.getRules()));

            newTempDisease.setRules(parseRuleTemplatesToTemporaryRule(rulesList));
            String[] symptomArr = parseJsonSymptomAndRiskFactors(form.getSymptoms());

            String[] riskFactorArr = parseJsonSymptomAndRiskFactors(form.getRiskFactors());


            Set<TempSymptom> tempSymptoms = createTempSymptomSet(getTemplateData(symptomArr));
            newTempDisease.setSymptoms(tempSymptoms);
            Set<TempRiskFactor> tempRiskFactors = createTempRiskFactorSet(getTemplateData(riskFactorArr));
            newTempDisease.setRiskFactors(tempRiskFactors);


            List<RuleForm> forms = parseRuleTemplatesToRuleForm(rulesList, getTemplateData(symptomArr)
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
    public Page<DiseaseDto> findAll(Pageable pageable, String plant){

            List<DiseaseDto> dtos = new ArrayList<>();
            long allDiseasesSize = 0L;
            Page<Disease> disease = null;
            if(plant.equals("_")){
                allDiseasesSize = diseaseRepository.findAll().size();
                disease = diseaseRepository.findAll(pageable);
            } else{
                PlantType type = plantTypeService.findByName(plant);
                allDiseasesSize = diseaseRepository.findAllByPlantType(type).size();
                disease = diseaseRepository.findAllByPlantType(pageable, type);
            }

            disease.forEach(disease1 -> {
                    dtos.add(new DiseaseDto(disease1.getName(), null, disease1.getDiseaseDescription(), disease1.getCount()));
            });
            return new PageImpl<>(dtos,pageable, allDiseasesSize);
    }



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

    public RuleTemplate[] parseRuleString(String toParse) throws JsonProcessingException {
        RuleTemplate[] rules = objectMapper.readValue(toParse, RuleTemplate[].class);
        return  rules;
    }


    public RuleForm parseSingleRuleTemplateToRuleForm(RuleTemplate template, Set<SimpleTemplateForm> symptoms,
                                                        Set<SimpleTemplateForm> riskFactors ){
        List<SimpleTemplateForm> symptomsList = new ArrayList<>(symptoms);
        List<SimpleTemplateForm> riskFactorsList = new ArrayList<>(riskFactors);

        RuleForm form = new RuleForm();
        form.setResult(template.getDiagnoseId());

        List<SimpleTemplateForm> rfs = template.getRiskFactorIndexes().stream().map(id -> riskFactorsList.get(id)).collect(Collectors.toList());
        form.setRiskFactorParts(rfs);

        List<SimpleTemplateForm> smp = template.getSymptomIndexes().stream().map(id -> symptomsList.get(id)).collect(Collectors.toList());
        form.setSymptomParts(smp);
        return form;
    }


    public List<RuleForm> parseRuleTemplatesToRuleForm(List<RuleTemplate> templates, Set<SimpleTemplateForm> symptoms,
                                                       Set<SimpleTemplateForm> riskFactors  ){

        return templates.stream().map(temp -> parseSingleRuleTemplateToRuleForm(temp, symptoms,riskFactors)).collect(Collectors.toList());
    }

    public TempRule parseSingleRuleTemplateToTemporaryRule(RuleTemplate template){
        TempRule rule = new TempRule();

        rule.setDiagnoseId(template.getDiagnoseId());
        rule.setRiskFactorIndexes(template.getRiskFactorIndexes());
        rule.setSymptomIndexes(template.getSymptomIndexes());
        return rule;
    }

    public List<TempRule> parseRuleTemplatesToTemporaryRule(List<RuleTemplate> templates){
        return templates.stream().map(t -> parseSingleRuleTemplateToTemporaryRule(t)).collect(Collectors.toList());
    }

}