package pl.mleczko.PlantExpertSystem.Validator;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mleczko.PlantExpertSystem.Exception.FileValidationException;
import pl.mleczko.PlantExpertSystem.Model.NewDiseaseForm;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewDiseaseFileValidatorTest {

    @Autowired
    private NewDiseaseFileValidator newDiseaseFileValidator;

    @Test
    void checkSegmentHeadlines_whenCorrectValues_ShouldReturnCorrectValue() {

        String[] headlines= new String[]{"CHOROBA", "SYMPTOMY", "CZYNNIKI RYZYKA", "KOMUNIKAT ZAPOBIEGAWCZY", "KOMUNIKAT INTERWENCYJNY", "REGUŁY WNIOSKUJĄCE", "OPIS CHOROBY"};

        assertTrue(newDiseaseFileValidator.checkSegmentHeadlines(headlines));
        assertFalse(newDiseaseFileValidator.checkSegmentHeadlines(new String[]{}));
    }

    @Test
    void checkSegmentHeadlines_whenNullValue_ShouldThrowExpetion() {
       assertFalse(newDiseaseFileValidator.checkSegmentHeadlines(null));
    }

}