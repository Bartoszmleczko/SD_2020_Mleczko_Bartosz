package pl.mleczko.PlantExpertSystem.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DiseaseCreationHandler {

    @ResponseBody
    @ExceptionHandler(DiseaseCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNewDiseaseValidation(DiseaseCreationException ex){
        return ex.getMessage();
    }


}
