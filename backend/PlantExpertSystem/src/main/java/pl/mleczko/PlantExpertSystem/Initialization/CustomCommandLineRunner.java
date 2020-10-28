package pl.mleczko.PlantExpertSystem.Initialization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.mleczko.PlantExpertSystem.Service.DiseaseService;

@Component
@Order
public class CustomCommandLineRunner implements CommandLineRunner {

    private final InitDatabaseData initDatabaseDataComponent;

    public CustomCommandLineRunner(InitDatabaseData initDatabaseDataComponent) {
        this.initDatabaseDataComponent = initDatabaseDataComponent;
    }

    @Override
    public void run(String... args) throws Exception {
        initDatabaseDataComponent.initPlantTypes();
        initDatabaseDataComponent.initRiskFactors();
        initDatabaseDataComponent.initSymptoms();
        initDatabaseDataComponent.initDiseases();
    }
}
