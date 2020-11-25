package pl.mleczko.PlantExpertSystem.Exception;

import org.springframework.stereotype.Service;


public class ObjectAlreadyExists extends  RuntimeException{

    public ObjectAlreadyExists(String className) {
        super("Taki obiekt juz istnieje.");
    }
}
