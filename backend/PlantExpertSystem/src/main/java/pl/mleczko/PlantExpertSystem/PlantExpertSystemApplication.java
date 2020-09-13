package pl.mleczko.PlantExpertSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PlantExpertSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantExpertSystemApplication.class, args);
	}


}
