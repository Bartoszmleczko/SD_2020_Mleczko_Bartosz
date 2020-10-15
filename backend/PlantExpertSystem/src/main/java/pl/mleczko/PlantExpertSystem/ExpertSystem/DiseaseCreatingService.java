package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.Disease;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;
import pl.mleczko.PlantExpertSystem.Entity.Symptom;
import pl.mleczko.PlantExpertSystem.Exception.ObjectAlreadyExists;
import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;
import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;
import pl.mleczko.PlantExpertSystem.Service.DiseaseService;
import pl.mleczko.PlantExpertSystem.Service.RiskFactorService;
import pl.mleczko.PlantExpertSystem.Service.SymptomService;

import java.io.*;
import java.util.Set;

@Service
public class DiseaseCreatingService {

    private final FileService fileService;
    private final DiseaseService diseaseService;
    private final RiskFactorService riskFactorService;
    private final SymptomService symptomService;


    public DiseaseCreatingService(FileService fileService, DiseaseService diseaseService, RiskFactorService riskFactorService, SymptomService symptomService) {
        this.fileService = fileService;
        this.diseaseService = diseaseService;
        this.riskFactorService = riskFactorService;
        this.symptomService = symptomService;
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

    public HttpStatus createNewDisease(NewDiseaseForm form) {

        String templateName = form.getDiseaseTemplateName();
        Disease disease = diseaseService.findByTemplateName(templateName);

        if(disease == null){
            Set<SimpleTemplateForm> factors = form.getRiskFactors();
            Set<SimpleTemplateForm> symptoms = form.getSymptoms();

            factors.forEach(factor -> {
                try {
                    writeRiskFactor(factor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            symptoms.forEach(symptom -> {
                try {
                    writeSymptom(symptom);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            return HttpStatus.CREATED;
        } else throw new ObjectAlreadyExists(Disease.class.getSimpleName());
    }
}
