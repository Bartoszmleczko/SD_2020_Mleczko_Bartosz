package pl.mleczko.PlantExpertSystem.Model;

import lombok.Getter;
import lombok.Setter;
import pl.mleczko.PlantExpertSystem.Entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto {

    private String email;

    private List<String> roles;

    private String firstName;

    private String lastName;


    public static UserDto convertToDto(User user){

        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRoles(user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()));
        return dto;


    }

}
