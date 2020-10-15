package pl.mleczko.PlantExpertSystem.Model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTemplateForm {

    @NotNull
    private String name;

    @NotNull
    private String templateName;

}
