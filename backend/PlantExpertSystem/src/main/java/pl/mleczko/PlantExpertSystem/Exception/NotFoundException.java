package pl.mleczko.PlantExpertSystem.Exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String className, Long id) {
        super(className + " with id: " + id + " does not exist");
    }
}