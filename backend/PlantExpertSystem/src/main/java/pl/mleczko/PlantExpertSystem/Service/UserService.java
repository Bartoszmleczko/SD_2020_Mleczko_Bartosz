package pl.mleczko.PlantExpertSystem.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.Role;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.Entity.VerificationToken;
import pl.mleczko.PlantExpertSystem.Model.UserDto;
import pl.mleczko.PlantExpertSystem.Repository.RoleRepository;
import pl.mleczko.PlantExpertSystem.Repository.UserRepository;
import pl.mleczko.PlantExpertSystem.Repository.VerificationTokenRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;


    public UserService(UserRepository userRepository, CustomUserDetailsService customUserDetailsService,
                       BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository,
                       VerificationTokenRepository verificationTokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
    }

    @Transactional
    public User findByUsername(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save (User user){
        User newUser = new User(user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName());
        Role basicRole = roleRepository.findByRoleName("ROLE_USER");
        newUser.setRoles(new HashSet<>(Arrays.asList(basicRole)));
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        newUser.setVerificationToken(token);
        String link = "http://localhost:4200/activate/" + token.getToken();
        mailService.sendEmail(user.getEmail(), "Aktywacja konta",mailService.prepareShortTermEmailBody(MailService.prepareEmail(link)) );
        return userRepository.save(newUser);
    }

    @Transactional
    public List<UserDto> findAllByUsername(Optional<String> username, String currentUser){
        List<User> matchingUsers = userRepository.findAllByUsername(username.orElse("_"));
        User user = findByUsername(currentUser);
        if(matchingUsers.contains(user))
        matchingUsers.remove(user);
        return matchingUsers.stream().map(u -> UserDto.convertToDto(u)).collect(Collectors.toList());
    }

    @Transactional
    public UserDto grantModeratorRole(UserDto dto) {
        User user = findByUsername(dto.getEmail());
        Role role = roleRepository.findByRoleName("ROLE_MODERATOR");
        if(!user.getRoles().contains(role)){
            Set<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
        }

        user = userRepository.save(user);
        return UserDto.convertToDto(user);
    }

    @Transactional
    public UserDto declineModeratorRole(UserDto dto) {

        User user = findByUsername(dto.getEmail());
        Role role = roleRepository.findByRoleName("ROLE_MODERATOR");
        if(user.getRoles().contains(role)){
            Set<Role> roles = user.getRoles();
            roles.remove(role);
            user.setRoles(roles);
        }
        user = userRepository.save(user);
        return UserDto.convertToDto(user);

    }

    public String activateUserAccount(String token) {
        VerificationToken dbToken = verificationTokenRepository.findByToken(token);
        System.out.println(token);
        if (dbToken != null) {
            User user = userRepository.findByVerificationToken(dbToken);
            System.out.println(user.getEmail());
            if (user != null) {
                user.setEnabled(true);
                userRepository.save(user);
                return "Pomyślnie aktywowano konto.";
            }
        }
        return "Błąd aktywacji konta. Błędny link aktywacyjny.";
    }
}
