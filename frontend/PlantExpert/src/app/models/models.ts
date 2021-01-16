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
  plant: string;
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
  url: SafeUrl;
}

export interface NewTemporaryDiseaseDto {
  plantType: string;
  diseaseName: string;
  diseaseDescription: string;
  precautionDiagnose: string;
  interventionDiagnose: string;
  riskFactors: string[];
  symptoms: string[];
  rules: RulePart[];
  image: File;
  properties: RefuseFormDto;
}

export interface DiagnoseDto {
  id: number;
  note: string;
  diseases: DiseaseDto[];
  creationTime: Date;
  riskFactors: RiskFactor[];
  symptoms: Symptom[];
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
  blocked: boolean;
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
  rules: RulePart[];
}

export interface RulePart {
  symptomIndexes: number[];
  riskFactorIndexes: number[];
  diagnoseId: number;
}

export interface UserDetails {
  firstName: string;
  lastName: string;
  joinDate: Date;
}

export interface UserPassword {
  oldPassword: string;
  newPassword: string;
}

export interface ContactMessageDto {
  id: number;
  header: string;
  content: string;
  creationDate: Date;
  answer: string;
  answerTime: string;
  status: string;
  email: string;
  firstName: string;
  lastName: string;
}

export interface RefuseFormDto {
  id: number;
  name: boolean;
  description: boolean;
  precautionDiagnose: boolean;
  interventionDiagnose: boolean;
  symptoms: boolean;
  riskFactors: boolean;
  rules: boolean;
}
