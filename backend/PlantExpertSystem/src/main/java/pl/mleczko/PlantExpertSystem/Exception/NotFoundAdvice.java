package pl.mleczko.PlantExpertSystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {


    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundException(NotFoundException ex){
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(ObjectAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String alreadyExistsException(ObjectAlreadyExists ex){
        return ex.getMessage();
    }

}
