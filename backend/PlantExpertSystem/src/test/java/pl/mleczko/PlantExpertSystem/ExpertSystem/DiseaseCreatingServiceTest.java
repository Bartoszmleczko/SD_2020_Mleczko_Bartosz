package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
                new TempRiskFactor(0, "Czynnik", "czynnik"),
                new TempRiskFactor(0, "Czynnik 2", "czynnik_2")
        ));

        RiskFactor rf1 = new RiskFactor();
        rf1.setName("Czynnik");
        rf1.setSlotName("czynnik");

        List<RiskFactor> dbRiskFactors = new ArrayList<>(Arrays.asList(rf1));


        assertIterableEquals(Set.of(new TempRiskFactor(0, "Czynnik 2", "czynnik_2")),
                diseaseCreatingService.getListOfNonExistingRiskFactors(tempRiskFactors, dbRiskFactors));
        assertEquals(1, diseaseCreatingService.getListOfNonExistingRiskFactors(tempRiskFactors, dbRiskFactors).size());

    }
}