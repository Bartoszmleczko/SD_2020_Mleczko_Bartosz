package pl.mleczko.PlantExpertSystem.ExpertSystem;

import jess.*;
import org.springframework.stereotype.Component;
import pl.mleczko.PlantExpertSystem.Model.PlantSicknessRequest;
import pl.mleczko.PlantExpertSystem.Model.RequestSlotDto;

import java.util.*;

@Component
public class PlantExpertEvalService {

    private Rete ruleEngine;


    public PlantExpertEvalService(Rete ruleEngine) {
        this.ruleEngine = ruleEngine;
    }


    public void batchJessFile() throws JessException {
        ruleEngine.reset();
        ruleEngine.clear();
        ruleEngine.batch("templates/plants.clp");

    }

    public Rete getEngine() throws JessException {
        return this.ruleEngine;
    }

    public List<String> provideFact(PlantSicknessRequest request) throws JessException {
        batchJessFile();

        List<Fact> facts = createFactFromRequest(request);

        for(Fact f : facts){
            ruleEngine.assertFact(f);
        }
        return evalAndRunEngine();
    }

    public List<Fact> createFactFromRequest(PlantSicknessRequest request) throws JessException {

        Set<RequestSlotDto> riskFactors = request.getRiskFactors();
        Set<RequestSlotDto> symptoms = request.getSymptoms();

        Fact riskFactorFact = new Fact("risk_factors", ruleEngine);
        Fact symptomFact = new Fact("symptoms", ruleEngine);
        if(riskFactors != null )
            if(!riskFactors.isEmpty())
        for(RequestSlotDto slot : riskFactors){
            riskFactorFact.setSlotValue(slot.getSlotName(), new Value(1, RU.INTEGER));
        }
        if(symptoms != null )
            if(!symptoms.isEmpty())
        for(RequestSlotDto slot: symptoms){
            symptomFact.setSlotValue(slot.getSlotName(), new Value(1, RU.INTEGER));
        }

        return Arrays.asList(riskFactorFact, symptomFact);

    }

    public List<String> provideFacts(List<Fact> facts) throws JessException {
        batchJessFile();
        for (Fact f : facts){
            ruleEngine.assertFact(f);
        }
        return  evalAndRunEngine();

    }

    public List<String> evalAndRunEngine() throws JessException {

        HashSet<String> varNames = new HashSet<>();

        ruleEngine.eval(("(facts)"));
        ruleEngine.run();
        ruleEngine.getGlobalContext().getVariableNames().forEachRemaining(j -> varNames.add(j.toString()));
        List<String> messages = getEngineReturnMessages(varNames);
        ruleEngine.reset();
        ruleEngine.clear();
        return messages;
    }

    public List<String> getEngineReturnMessages(Set<String> varNames){
        List<String> messages = new ArrayList<>();
        varNames.forEach(j -> {
            try {
                Value v = ruleEngine.getGlobalContext().getVariable(j);
                messages.add(v.stringValue(ruleEngine.getGlobalContext()));
                System.out.println(v.stringValue(ruleEngine.getGlobalContext()));
            } catch (JessException e) {
                e.printStackTrace();
            }
        });
        return messages;
    }

}
