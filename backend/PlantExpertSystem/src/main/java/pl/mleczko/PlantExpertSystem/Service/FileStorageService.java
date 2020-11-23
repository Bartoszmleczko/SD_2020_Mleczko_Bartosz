package pl.mleczko.PlantExpertSystem.Service;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final String path = new File("src/main/resources/images").getAbsolutePath();

    private Path uploadPath;


    public void initDir(String username) {
        this.uploadPath = Paths.get(path + File.separator +username);
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public String getDescriptionTxtFile(String name) throws IOException {

        String description = new String(Files.readAllBytes(Paths.get("src/main/resources/descriptions/" + name)));
        return description;
    }

    public byte[] getImageFile(String name) {
        File image = new File("src/main/resources/images/" + name);
        byte[] array = null;
        try {
            array = FileUtil.readAsByteArray(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }


    public void store (MultipartFile file, String name){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if(file.isEmpty() )
            throw new RuntimeException("Failed to store empty file" + filename);

        if(filename.contains("..") )
            throw new RuntimeException("Given filename is not valid " + filename);

        try (InputStream inputStream = file.getInputStream()) {

            Path path2 = Paths.get(this.path);

            Files.copy(inputStream,path2.resolve(name + "." + FilenameUtils.getExtension(file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void deleteImage(String imagePath){

        Path path = Paths.get(this.path);
        File file = new File(path.resolve(imagePath).toString());
        file.delete();
    }

    public Path getUploadPath() {
        return uploadPath;
    }

    public String getPath() {
        return path;
    }

}
