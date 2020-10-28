package pl.mleczko.PlantExpertSystem.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTemplateForm {

    @NotNull
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String templateName;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof SimpleTemplateForm)) {
            return false;
        }
        SimpleTemplateForm f = (SimpleTemplateForm) obj;

        return name.equals(f.getName()) && templateName.equals(f.getTemplateName()) && id == f.getId();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + id;
        result = 31 * result + templateName.hashCode();
        return result;

    }
}
