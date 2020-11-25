package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TemporaryDiseaseTemplateForm {

    private MultipartFile image;

    private String plantType;

    private String diseaseName;

    private String diseaseDescription;

    private String precautionDiagnose;

    private String interventionDiagnose;

    private String riskFactors;

    private String symptoms;

    private String rules;


}
