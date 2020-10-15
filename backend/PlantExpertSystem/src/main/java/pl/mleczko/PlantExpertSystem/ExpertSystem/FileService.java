package pl.mleczko.PlantExpertSystem.ExpertSystem;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FileService {

    private final List<String> fileNames = new ArrayList<>(Arrays.asList("templates/diseases.clp",
            "templates/risk_factors.clp", "templates/symptoms.clp", "templates/rules.clp"));

//    private final String directoryName = new File("src/main/resources/templates").getAbsolutePath();
//
//    private List<String> addDirectoryPrefix(){
//        List<String> filesWithPrefix = fileNames;
//        filesWithPrefix.forEach(name -> new String("templates/").concat(name));
//        return filesWithPrefix;
//     }
     public List<String> getFileNames(){
        return this.fileNames;
     }

     private Resource getResourceFile(String path){
        return new ClassPathResource(path);
     }

     public Map<String, Resource> getAllResources(){

        Map<String, Resource> resources = new HashMap<>();
        this.fileNames.forEach(name -> {
            resources.put(name, getResourceFile(name));
        });
        return resources;
     }

     public File getFile(String name) throws IOException {
        Resource file =  getAllResources().get(name);
         System.out.println(file);
        return file.getFile();
     }




}
