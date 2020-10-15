export interface Plant{
    name: string;
    templateName: string;
}

export interface RiskFactor{
    riskId: number;
    name: string;
    slotName: string;
    factorType: string;
    plantType: string;
}

export interface RiskFactorWrapper{
    map: Map<string, RiskFactor[]>;
}

export interface Symptom{
    symptomId: number;
    name: string;
    slotName: string;
    plantType: string;
}


export interface DiagnoseForm{

    riskFactors: RiskFactorWrapper;
    symptoms: Symptom[];

}
