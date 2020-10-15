package pl.mleczko.PlantExpertSystem.Exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String className) {
        super(className + " with requested parameter does not exist");
    }
}