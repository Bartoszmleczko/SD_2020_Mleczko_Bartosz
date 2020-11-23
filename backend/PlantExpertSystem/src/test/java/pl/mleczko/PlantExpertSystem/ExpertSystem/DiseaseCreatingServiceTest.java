package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mleczko.PlantExpertSystem.Entity.FactorType;
import pl.mleczko.PlantExpertSystem.Entity.RiskFactor;
import pl.mleczko.PlantExpertSystem.Entity.TempRiskFactor;
import pl.mleczko.PlantExpertSystem.Entity.TempSymptom;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiseaseCreatingServiceTest {

    @Autowired
    private DiseaseCreatingService diseaseCreatingService;

    @Test
    void getListOfNonExistingSymptoms() {




    }

    @Test
    void getListOfNonExistingRiskFactors() {
        Set<TempRiskFactor> tempRiskFactors = new HashSet<>(Arrays.asList(
                new TempRiskFactor(0, "Czynnik", "czynnik", FactorType.OTHER),
                new TempRiskFactor(1, "Czynnik 2", "czynnik_2", FactorType.OTHER),
                new TempRiskFactor(2, "Czynnik 3", "czynnik_3", FactorType.OTHER),
                new TempRiskFactor(3, "Czynnik 4", "czynnik_4", FactorType.OTHER)
        ));

        RiskFactor rf1 = new RiskFactor();
        rf1.setName("Czynnik");
        rf1.setSlotName("czynnik");
        RiskFactor rf2 = new RiskFactor();
        rf2.setName("Czynnik 4");
        rf2.setSlotName("czynnik_4");

        List<RiskFactor> dbRiskFactors = new ArrayList<>(Arrays.asList(rf1, rf2));

        Set<TempRiskFactor> rrf = new HashSet<>(Arrays.asList(
                new TempRiskFactor(1, "Czynnik 2", "czynnik_2",FactorType.OTHER),
                new TempRiskFactor(2, "Czynnik 3", "czynnik_3", FactorType.OTHER)
        ));

        List<TempRiskFactor> lrf = diseaseCreatingService.getListOfNonExistingRiskFactors(tempRiskFactors, dbRiskFactors);

        assertIterableEquals(rrf, lrf);
        assertEquals(2, diseaseCreatingService.getListOfNonExistingRiskFactors(tempRiskFactors, dbRiskFactors).size());

    }
}