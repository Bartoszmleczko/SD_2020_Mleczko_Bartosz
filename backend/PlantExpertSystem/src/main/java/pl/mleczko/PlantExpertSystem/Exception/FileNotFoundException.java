package pl.mleczko.PlantExpertSystem.Exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException() {
        super("File with that name not found");
    }
}
