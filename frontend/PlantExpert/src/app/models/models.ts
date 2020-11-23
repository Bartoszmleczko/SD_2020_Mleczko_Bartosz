import { FormGroup } from "@angular/forms";
import { SafeUrl } from "@angular/platform-browser";
export interface Plant {
  id: number;
  name: string;
  requestDate: Date;
}

export interface RiskFactor {
  riskId: number;
  name: string;
  slotName: string;
  factorType: string;
  plantType: string;
}

export interface RequestSlotDto {
  name: string;
  slotName: string;
}

export interface Symptom {
  symptomId: number;
  name: string;
  slotName: string;
  plantType: string;
  checked: boolean;
}

export interface DiagnoseForm {
  riskFactors: Map<string, RiskFactor[]>;
  symptoms: Symptom[];
}

export interface PlantSicknessRequest {
  riskFactors: RequestSlotDto[];
  symptoms: RequestSlotDto[];
}

export interface DiseaseDto {
  diseaseName: string;
  diagnose: string;
  diseaseDescription: string;
  url: SafeUrl;
}

export interface TemporaryDiseaseDto {
  id: number;
  diseaseName: string;
  diseaseTemplateName: string;
  description: string;
  precautionDiagnose: string;
  interventionDiagnose: string;
  riskFactors: RiskFactor[];
  symptoms: Symptom[];
  form: FormGroup;
}

export interface NewTemporaryDiseaseDto {
  plantType: string;
  diseaseName: string;
  diseaseDescription: string;
  precautionDiagnose: string;
  interventionDiagnose: string;
  riskFactors: string[];
  symptoms: string[];
  rules: string[];
  image: File;
}

export interface DiagnoseDto {
  id: number;
  note: string;
  diseases: DiseaseDto[];
  creationTime: Date;
}

export interface ExtendedDiseaseDto {
  id: number;
  diseaseName: string;
  diseasesDescription: string;
  precautionDiagnose: string;
  interventionsDiagnose: string;
  url: SafeUrl;
  count: number;
}

export interface UserDto {
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
}

export interface TemplatePart {
  name: string;
  toDisable: boolean;
}
export interface Templates {
  riskFactors: TemplatePart[];
  symptoms: TemplatePart[];
  precautionDiagnose: string;
  interventionDiagnose: string;
}

export interface RulePart {
  shortName: string;
  fullName: string;
}
