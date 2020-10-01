package pl.mleczko.PlantExpertSystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.mleczko.PlantExpertSystem.Exception.AuthEntryPointJwt;
import pl.mleczko.PlantExpertSystem.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, AuthEntryPointJwt authEntryPointJwt) {
        this.customUserDetailsService = customUserDetailsService;
        this.authEntryPointJwt = authEntryPointJwt;
    }

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
     return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/register").permitAll().
                antMatchers("/login").permitAll().anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).
                and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
