package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.Role;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.Repository.RoleRepository;
import pl.mleczko.PlantExpertSystem.Repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    public UserService(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User findByUsername(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save (User user){
        User newUser = new User(user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName());
        Role basicRole = roleRepository.findByRoleName("USER");
        newUser.setRoles(new HashSet<>(Arrays.asList(basicRole)));
        return userRepository.save(newUser);
    }

}
