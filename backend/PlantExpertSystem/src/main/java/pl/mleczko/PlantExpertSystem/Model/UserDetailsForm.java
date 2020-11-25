package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDetailsForm {

    private String firstName;
    private String lastName;
    private LocalDateTime joinDate;
}
