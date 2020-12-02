package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlainTemporaryDiseaseTemplateForm {

    private Long id;

    private String plantType;

    private String diseaseName;

    private String diseaseDescription;

    private String precautionDiagnose;

    private String interventionDiagnose;

    private String riskFactors;

    private String symptoms;

    private String rules;

    private RefuseTempDiseaseDto properties;
}
