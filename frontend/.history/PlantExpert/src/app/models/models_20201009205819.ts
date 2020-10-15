
export interface Plant{
    name: string;
    templateName: string;
}

export interface RiskFactor {
    riskId: number;
    name: string;
    slotName: string;
    factorType: string;
    plantType: string;
}

export interface RiskFactorWrapper{
    factors: RiskFactor[];
}

export interface Symptom {
    symptomId: number;
    name: string;
    slotName: string;
    plantType: string;
    checked: boolean;
}


export interface DiagnoseForm {

    riskFactors: Map<string, RiskFactorWrapper>;
    symptoms: Symptom[];

}
