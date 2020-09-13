package pl.mleczko.PlantExpertSystem.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String email;

    private String firstName;

    private String lastName;

    private List<String> roles = new ArrayList<>();

    private String jwtToken;

    public LoginResponse(String email, String firstName, String lastName, List<String> roles, String jwtToken) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }
}
