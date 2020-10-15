package pl.mleczko.PlantExpertSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WikipediaApiResponse {

    private String title;
    private String text;
    private String image;


}
