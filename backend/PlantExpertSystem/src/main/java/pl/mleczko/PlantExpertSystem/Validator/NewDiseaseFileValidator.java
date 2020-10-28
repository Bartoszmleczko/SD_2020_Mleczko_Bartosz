package pl.mleczko.PlantExpertSystem.Validator;

import org.springframework.stereotype.Component;
import pl.mleczko.PlantExpertSystem.Exception.FileValidationException;

@Component
public class NewDiseaseFileValidator {

    private final static int segmentNumber = 7;
    private final static String[] headlines = {"CHOROBA", "SYMPTOMY", "CZYNNIKI RYZYKA", "KOMUNIKAT ZAPOBIEGAWCZY", "KOMUNIKAT INTERWENCYJNY", "REGUŁY WNIOSKUJĄCE", "OPIS CHOROBY"};

    public boolean isSegmentNumberRight(int size){
        return size == segmentNumber;
    }

    public boolean checkSegmentHeadlines(String[] segments){
        if(segments!=null ){
            if(segments.length > 0){
                String[] fileHeadlines = new String[segments.length];
                for(int i = 0; i<segments.length; i++){
                    String[] temp = segments[i].split(":");
                    fileHeadlines[i] = temp[0];
                }
                for(int i = 0; i<headlines.length; i++ ){
                    if(!headlines[i].equals(fileHeadlines[i]))
                        return false;
                }
                return true;
            }
        }return false;


    }


}
