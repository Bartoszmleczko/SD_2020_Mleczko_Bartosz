package pl.mleczko.PlantExpertSystem.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String email;

    private String password;


}
