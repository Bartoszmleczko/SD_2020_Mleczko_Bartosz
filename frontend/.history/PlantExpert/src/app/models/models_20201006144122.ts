export interface Plant{
    name: String;
    templateName: String;
}

export interface RiskFactor{
    riskId: number,
    name: string,
    slotName: string,
    factorType: string,
    plantType: string
}

export interface RiskFactorWrapper{
    CULTIVATION: RiskFactor[];
    MOISTURE: RiskFactor[];
    OTHER: RiskFactor[];
}

export interface Symptom{
    symptomId: number,
    name: string,
    slotName: string,
    plantType: string
}


export interface DiagnoseForm{

    riskFactors: RiskFactorWrapper;
    symptoms: Symptom[];

}
