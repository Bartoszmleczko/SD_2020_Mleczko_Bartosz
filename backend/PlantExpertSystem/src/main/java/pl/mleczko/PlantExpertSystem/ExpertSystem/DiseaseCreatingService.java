package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;
import pl.mleczko.PlantExpertSystem.Model.SimpleTemplateForm;

import java.io.*;
import java.util.Set;

@Service
public class DiseaseCreatingService {

    private final FileService fileService;



    public DiseaseCreatingService(FileService fileService) {
        this.fileService = fileService;
    }

    public void writeRiskFactor(SimpleTemplateForm form) throws IOException {
        File file = fileService.getFile("templates/risk_factors.clp");
        File file2 = new ClassPathResource("templates/try.txt").getFile();
        String factor = replaceSlotTemplate(form.getTemplateName());
        writeRawTemplateData(file2, factor);
    }

    public void writeSymptom(SimpleTemplateForm form) throws IOException {
        File file = fileService.getFile("templates/symptoms.clp");
        String factor = replaceSlotTemplate(form.getTemplateName());
        writeRawTemplateData(file, factor);
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

    public String createNewDisease(NewDiseaseForm form) {

        Set<SimpleTemplateForm> factors = form.getRiskFactors();
        Set<SimpleTemplateForm> symptoms = form.getSymptoms();

        String templateName = form.getDiseaseTemplateName();

        //TODO: end the mechanism


        return null;
    }
}
