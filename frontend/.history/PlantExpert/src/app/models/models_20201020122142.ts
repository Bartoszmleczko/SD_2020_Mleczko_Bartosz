import { SafeUrl } from "@angular/platform-browser";
export interface Plant {
  id: number;
  name: string;
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
