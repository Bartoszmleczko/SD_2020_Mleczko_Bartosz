package pl.mleczko.PlantExpertSystem.Service;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Entity.Role;
import pl.mleczko.PlantExpertSystem.Entity.User;
import pl.mleczko.PlantExpertSystem.Repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(s);
        List<GrantedAuthority> roles = getUserRoles(user.getRoles());
        return buildUserForAuthentication(user, roles);
    }

    public List<GrantedAuthority> getUserRoles(Set<Role> roles){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role r: roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        return grantedAuthorities;
    }

    public UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> roles){
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.isEnabled(),
                true,true,true, roles);
    }

    public  User findLoggedInUsername(){
        return userRepository.findUserProfileIdByCurrentUser();
    }

}
