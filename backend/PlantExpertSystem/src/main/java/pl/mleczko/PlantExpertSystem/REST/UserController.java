package pl.mleczko.PlantExpertSystem.REST;

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
import pl.mleczko.PlantExpertSystem.Model.LoginRequest;
import pl.mleczko.PlantExpertSystem.Model.LoginResponse;
import pl.mleczko.PlantExpertSystem.Model.PlantSicknessRequest;
import pl.mleczko.PlantExpertSystem.Security.JwtUtils;
import pl.mleczko.PlantExpertSystem.Service.UserService;

import java.util.List;
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

    @PostMapping("/register")
    public  String response(@RequestBody User user){
        userService.save(user);
        return "User registered";
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

}








