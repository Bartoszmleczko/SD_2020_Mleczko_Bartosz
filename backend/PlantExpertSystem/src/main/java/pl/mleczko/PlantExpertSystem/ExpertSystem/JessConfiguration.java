package pl.mleczko.PlantExpertSystem.ExpertSystem;

import jess.Rete;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JessConfiguration {


    @Bean
    public Rete getRete(){
        return new Rete();
    }

}
