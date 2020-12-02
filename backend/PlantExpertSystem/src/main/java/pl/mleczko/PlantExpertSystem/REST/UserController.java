package pl.mleczko.PlantExpertSystem.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Response;
import jess.JessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.ExpertSystem.PlantExpertEvalService;
import pl.mleczko.PlantExpertSystem.Model.*;
import pl.mleczko.PlantExpertSystem.Security.JwtUtils;
import pl.mleczko.PlantExpertSystem.Service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PlantExpertEvalService plantExpertEvalService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PlantExpertEvalService plantExpertEvalService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.plantExpertEvalService = plantExpertEvalService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/home")
    public String getHome(){
        return "Home";
    }

    @PostMapping("/register" )
    public  ResponseEntity<String> response(@RequestBody User user){
        userService.save(user);
        return ResponseEntity.ok("Pomyślnie zarejestrowano");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User dbUser = userService.findByUsername(userDetails.getUsername());
        List<String> roles = userDetails.getAuthorities().stream().map( role -> ((GrantedAuthority) role).getAuthority()).collect(Collectors.toList());
        String username = userDetails.getUsername();
        String firstName = dbUser.getFirstName();
        String lastName = dbUser.getLastName();
        LoginResponse response = new LoginResponse(username, firstName, lastName, roles, jwt );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam Optional<String> username, Principal principal){
        return ResponseEntity.ok(userService.findAllByUsername(username, principal.getName()));
    }

    @PutMapping("/users/grantMod")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> grantModeratorRole(@RequestBody UserDto dto){
        return ResponseEntity.ok(userService.grantModeratorRole(dto));
    }

    @PutMapping("/users/declineMod")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> declineModeratorRole(@RequestBody UserDto dto){
        return ResponseEntity.ok(userService.declineModeratorRole(dto));
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<ActivationMessage> activateUserAccount(@PathVariable String token){

        return ResponseEntity.ok(new ActivationMessage(userService.activateUserAccount(token)));
    }

    @PutMapping("/users/details")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> changeUserDetails(@RequestBody UserDetailsForm form, Principal principal){
        return ResponseEntity.ok(userService.changeUserDetails(form, principal));
    }

    @GetMapping("/users/details")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserDetailsForm> getUserDetails(Principal principal){
        return ResponseEntity.ok(userService.getUserDetails(principal.getName()));
    }

}








