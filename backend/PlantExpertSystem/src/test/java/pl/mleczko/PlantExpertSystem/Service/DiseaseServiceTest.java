//package pl.mleczko.PlantExpertSystem.Service;
//
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import pl.mleczko.PlantExpertSystem.Exception.FileValidationException;
//import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;
//import pl.mleczko.PlantExpertSystem.Model.RuleForm;
//import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class DiseaseServiceTest {
//
//    @Autowired
//    private DiseaseService diseaseService;
//
//
//    @Test
//    void splitStringToSegments_whenContentIsNullOrEmptyString_ShouldThrowException() {
//
//       assertThrows(FileValidationException.class, () -> diseaseService.splitStringToSegments(null, ","));
//       assertThrows(FileValidationException.class,() -> diseaseService.splitStringToSegments("", ","));
//    }
//
//    @Test
//    void splitStringToSegments_whenCorrectData_shouldReturnCorrectArray(){
//        assertArrayEquals(new String[]{"1", "2", "3"}, diseaseService.splitStringToSegments("1,2,3", ","));
//        assertArrayEquals(new String[]{"1", "2", "3"}, diseaseService.splitStringToSegments("1,2,3, ", ","));
//        assertArrayEquals(new String[]{"1", "2", "3"}, diseaseService.splitStringToSegments("  ,1,2,3,  ", ","));
//    }
//
//    @Test
//    void getSegmentContent_whenContentIsNull_ShouldThrowException() {
//        assertThrows(FileValidationException.class,() -> diseaseService.getSegmentContent(null));
//    }
//
//    @Test
//    void getSegmentContent_whenSegmentsAreCorrect_ShouldReturnMapWithCorrectValues(){
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", null);
//        map.put("TEST", new String[]{"TEST"});
//
//        String[] segments = new String[]{
//                "CHOROBA:Dwuczęściowa nazwa",
//                "SYMPTOMY: Symptom, Symptom 2 ",
//                "CZYNNIKI RYZYKA:Czynnik,Czynnik 2",
//                "KOMUNIKAT ZAPOBIEGAWCZY:",
//                "TEST::: ,,TEST,"
//        };
//
//        assertEquals(map.get("KOMUNIKAT ZAPOBIEGAWCZY"), diseaseService.getSegmentContent(segments).get("KOMUNIKAT ZAPOBIEGAWCZY"));
//        assertArrayEquals(map.get("SYMPTOMY"),diseaseService.getSegmentContent(segments).get("SYMPTOMY"));
//        assertArrayEquals(map.get("CZYNNIKI RYZYKA"), diseaseService.getSegmentContent(segments).get("CZYNNIKI RYZYKA"));
//        assertArrayEquals(map.get("TEST"), diseaseService.getSegmentContent(segments).get("TEST"));
//    }
//
//    @Test
//    void prepareTemplate_whenCorrectInputData_shouldReturnCorrectReturnValue() {
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"   Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", null);
//        map.put("KOMUNIKAT INTERWENCYJNY", new String[]{"Komunikat interwencyjny"});
//        map.put("REGUŁY WNIOSKUJĄCE", new String[]{"S0 + R1 = 0", "S1 = 1"});
//        map.put("OPIS CHOROBY", new String[]{"Opis choroby"});
//
//        NewDiseaseForm form = new NewDiseaseForm();
//
//        String diseaseName = "Dwuczęściowa nazwa";
//        form.setDiseaseName(diseaseName);
//        String templateName = "dwuczesciowa_nazwa";
//        form.setDiseaseTemplateName(templateName);
//        String desc = "Opis choroby";
//
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0,"Symptom", "symptom"),
//                new SimpleTemplateForm(1, "Symptom 2", "symptom_2")
//        ));
//        form.setSymptoms(symptoms);
//        Set<SimpleTemplateForm> riskFactors = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0,"Czynnik", "czynnik"),
//                new SimpleTemplateForm(1,"Czynnik 2","czynnik_2")
//        ));
//
//        form.setRiskFactors(riskFactors);
//        form.setDescription("Opis choroby");
//
//
//        assertTrue(diseaseName.equals(diseaseService.prepareTemplate(map).getDiseaseName()));
//        assertTrue(templateName.equals(diseaseService.prepareTemplate(map).getDiseaseTemplateName()));
//        assertTrue("Opis choroby".equals(diseaseService.prepareTemplate(map).getDescription()));
//
//    }
//
//    @Test
//    public void prepareTemplate_whenCorrectRiskFactors_shouldReturnCorrectValues(){
//
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"   Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", null);
//        map.put("KOMUNIKAT INTERWENCYJNY", new String[]{"Komunikat interwencyjny"});
//        map.put("REGUŁY WNIOSKUJĄCE", new String[]{"S0 + R1 = 0", "S1 = 1"});
//        map.put("OPIS CHOROBY", new String[]{"Opis"});
//
//        NewDiseaseForm form = new NewDiseaseForm();
//
//        String diseaseName = "Dwuczęściowa nazwa";
//        form.setDiseaseName(diseaseName);
//        String templateName = "dwuczesciowa_nazwa";
//        form.setDiseaseTemplateName(templateName);
//
//        Set<SimpleTemplateForm> riskFactors = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Czynnik", "czynnik"),
//                new SimpleTemplateForm(1,"Czynnik 2","czynnik_2")
//        ));
//        form.setRiskFactors(riskFactors);
//        Set<SimpleTemplateForm> rf = diseaseService.prepareTemplate(map).getRiskFactors();
//
//        assertIterableEquals(riskFactors,rf);
//    }
//
//    @Test
//    public void prepareTemplate_whenCorrectSymptoms_shouldReturnCorrectValues(){
//
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"   Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", null);
//        map.put("KOMUNIKAT INTERWENCYJNY", new String[]{"Komunikat interwencyjny"});
//        map.put("REGUŁY WNIOSKUJĄCE", new String[]{"S0 + R1 = 0", "S1 = 1"});
//        map.put("OPIS CHOROBY", new String[]{"Opis"});
//
//        NewDiseaseForm form = new NewDiseaseForm();
//
//        String diseaseName = "Dwuczęściowa nazwa";
//        form.setDiseaseName(diseaseName);
//        String templateName = "dwuczesciowa_nazwa";
//        form.setDiseaseTemplateName(templateName);
//
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0,"Symptom", "symptom"),
//                new SimpleTemplateForm(1, "Symptom 2", "symptom_2")
//        ));
//        form.setSymptoms(symptoms);
//
//
//        assertIterableEquals(symptoms, diseaseService.prepareTemplate(map).getSymptoms());
//    }
//
//    @Test
//    public void prepareTemplate_whenCorrectRules_ShouldReturnCorrectArray(){
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"   Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", null);
//        map.put("KOMUNIKAT INTERWENCYJNY", new String[]{"Komunikat interwencyjny"});
//        map.put("REGUŁY WNIOSKUJĄCE", new String[]{"S0 + R1 = 0", "S1 = 1"});
//        map.put("OPIS CHOROBY", new String[]{"Opis"});
//
//        NewDiseaseForm form = new NewDiseaseForm();
//
//        String diseaseName = "Dwuczęściowa nazwa";
//        form.setDiseaseName(diseaseName);
//        String templateName = "dwuczesciowa_nazwa";
//        form.setDiseaseTemplateName(templateName);
//
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0,"Symptom", "symptom"),
//                new SimpleTemplateForm(1,"Symptom 2", "symptom_2")
//        ));
//        form.setSymptoms(symptoms);
//        Set<SimpleTemplateForm> riskFactors = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0,"Czynnik", "czynnik"),
//                new SimpleTemplateForm(1,"Czynnik 2","czynnik_2")
//        ));
//        form.setRiskFactors(riskFactors);
//
//        String[] rules = new String[]{"S0 + R1 = 0", "S1 = 1"};
//
//        assertArrayEquals(rules, diseaseService.prepareTemplate(map).getRules());
//    }
//
//    @Test
//    void prepareTemplate_whenCorrectDiagnoses_shouldReturnCorrectReturnValue() {
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"   Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", null);
//        map.put("KOMUNIKAT INTERWENCYJNY", new String[]{"Komunikat interwencyjny"});
//        map.put("REGUŁY WNIOSKUJĄCE", new String[]{"S0 + R1 = 0", "S1 = 1"});
//        map.put("OPIS CHOROBY", new String[]{"Opis choroby"});
//
//        NewDiseaseForm form = new NewDiseaseForm();
//
//        String diseaseName = "Dwuczęściowa nazwa";
//        form.setDiseaseName(diseaseName);
//        String templateName = "dwuczesciowa_nazwa";
//        form.setDiseaseTemplateName(templateName);
//        String desc = "Opis choroby";
//
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0,"Symptom", "symptom"),
//                new SimpleTemplateForm(1, "Symptom 2", "symptom_2")
//        ));
//        form.setSymptoms(symptoms);
//        Set<SimpleTemplateForm> riskFactors = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Czynnik", "czynnik"),
//                new SimpleTemplateForm(1, "Czynnik 2","czynnik_2")
//        ));
//
//        form.setRiskFactors(riskFactors);
//        form.setDescription("Opis choroby");
//
//        String inter = "Komunikat interwencyjny";
//
//        assertEquals( null, diseaseService.prepareTemplate(map).getPrecautionDiagnose());
//        assertTrue( inter.equals(diseaseService.prepareTemplate(map).getInterventionDiagnose()));
//
//    }
//
//    @Test
//    void prepareTemplate_whenIncorrectDiagnoses_shouldReturnCorrectReturnValue() {
//        Map<String, String[]> map = new HashMap<>();
//        map.put("CHOROBA", new String[]{"Dwuczęściowa nazwa"});
//        map.put("SYMPTOMY", new String[]{"   Symptom", "Symptom 2"});
//        map.put("CZYNNIKI RYZYKA", new String[]{"Czynnik", "Czynnik 2"});
//        map.put("KOMUNIKAT ZAPOBIEGAWCZY", new String[]{" ", "   "});
//        map.put("KOMUNIKAT INTERWENCYJNY", new String[]{"   "," "});
//        map.put("REGUŁY WNIOSKUJĄCE", new String[]{"S0 + R1 = 0", "S1 = 1"});
//        map.put("OPIS CHOROBY", new String[]{"Opis choroby"});
//
//        NewDiseaseForm form = new NewDiseaseForm();
//
//        String diseaseName = "Dwuczęściowa nazwa";
//        form.setDiseaseName(diseaseName);
//        String templateName = "dwuczesciowa_nazwa";
//        form.setDiseaseTemplateName(templateName);
//        String desc = "Opis choroby";
//
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Symptom", "symptom"),
//                new SimpleTemplateForm(1,"Symptom 2", "symptom_2")
//        ));
//        form.setSymptoms(symptoms);
//        Set<SimpleTemplateForm> riskFactors = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Czynnik", "czynnik"),
//                new SimpleTemplateForm(1, "Czynnik 2","czynnik_2")
//        ));
//
//        form.setRiskFactors(riskFactors);
//        form.setDescription("Opis choroby");
//
//        String inter = "Komunikat interwencyjny";
//
//        assertEquals( null, diseaseService.prepareTemplate(map).getPrecautionDiagnose());
//        assertEquals( null,diseaseService.prepareTemplate(map).getInterventionDiagnose());
//
//    }
//
//
//
//    @Test
//    void getTemplateData_whenCorrectStringArray_shouldReturnCorrectValues() {
//        String[] templates = new String[]{"Czynnik 1", "    Czynnik 2 ", " ", ""};
//        Set<SimpleTemplateForm> results = new HashSet<SimpleTemplateForm>(Arrays.asList(
//                new SimpleTemplateForm(0, "Czynnik 1", "czynnik_1"),
//                new SimpleTemplateForm(1, "Czynnik 2", "czynnik_2")
//        ));
//        assertIterableEquals(results, diseaseService.getTemplateData(templates));
//    }
//
//    @Test
//    void getTemplateData_whenIncorrectStringArray_shouldThrowException() {
//
//        assertThrows(FileValidationException.class, () -> diseaseService.getTemplateData(null));
//        assertThrows(FileValidationException.class, () -> diseaseService.getTemplateData(new String[]{}));
//    }
//
//    @Test
//    void convertToLowerCaseWithUnderscores_whenCorrectString_shouldReturnCorrectValues() {
//
//        String name1 = "ĘĄscztół";
//        assertTrue("eascztol".equals(diseaseService.convertToLowerCaseWithUnderscores(name1)));
//    }
//
//    @Test
//    void convertToLowerCase_whenNullString_shouldThrowException(){
//        assertThrows(FileValidationException.class, () -> diseaseService.convertToLowerCaseWithUnderscores(null));
//    }
//
//    @Test
//    void parseSingleRule_whenCorrectValues_shouldReturnCorrectValues() {
//
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Symptom", "symptom"),
//                new SimpleTemplateForm(1,"Symptom 2", "symptom_2")
//        ));
//
//        Set<SimpleTemplateForm> riskFactors = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Czynnik", "czynnik"),
//                new SimpleTemplateForm(1, "Czynnik 2","czynnik_2")
//        ));
//
//
//        String rawRule = "R1+S0=1";
//
//        RuleForm ruleForm = new RuleForm();
//        List<SimpleTemplateForm> symptomParts = new ArrayList<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Symptom", "symptom")
//        ));
//        List<SimpleTemplateForm> riskFactorsParts = new ArrayList<>(Arrays.asList(
//                new SimpleTemplateForm(1, "Czynnik 2", "czynnik_2")
//        ));
//        ruleForm.setSymptomParts(symptomParts);
//        ruleForm.setRiskFactorParts(riskFactorsParts);
//        ruleForm.setResult(1);
//        assertIterableEquals(symptomParts, diseaseService.parseSingleRule(rawRule, symptoms,riskFactors).getSymptomParts());
//        assertIterableEquals(riskFactorsParts, diseaseService.parseSingleRule(rawRule, symptoms,riskFactors).getRiskFactorParts());
//
//    }
//
//    @Test
//    void findTemplateForm_whenCorrectValues_shouldReturnCorrectValues() {
//        Set<SimpleTemplateForm> symptoms = new HashSet<>(Arrays.asList(
//                new SimpleTemplateForm(0, "Symptom", "symptom"),
//                new SimpleTemplateForm(1,"Symptom 2", "symptom_2")
//        ));
//        SimpleTemplateForm check =  new SimpleTemplateForm(1,"Symptom 2", "symptom_2");
//
//        assertEquals(check, diseaseService.findTemplateForm(symptoms,1));
//
//    }
//
//    @Test
//    void findTemplateForm_wheNullSet_shouldThrowException(){
//        assertThrows(FileValidationException.class,() ->  diseaseService.findTemplateForm(null,1));
//    }
//
//}