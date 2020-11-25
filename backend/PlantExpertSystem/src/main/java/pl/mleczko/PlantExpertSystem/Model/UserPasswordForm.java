package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordForm {

    private String oldPassword;
    private String newPassword;

}
