package pl.mleczko.PlantExpertSystem.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import pl.mleczko.PlantExpertSystem.Model.WikipediaApiResponse;

import java.io.IOException;

@Service
public class WikipediaApiService {

    private final String BASE_URL="https://pl.wikipedia.org/api/rest_v1/page/summary/";



    public WikipediaApiResponse getData(String subject) {
        String displayTitle="";
        String imageURL="";
        String extractText="";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL+subject)
                .get()
                .build();
        WikipediaApiResponse res = null;
        try {
            Response response=client.newCall(request).execute();
            String data = response.body().string();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(data);

            //get title from JSON response
            displayTitle= (String) jsonObject.get("displaytitle");

            //first create a image object and then get image URL
            JSONObject jsonObjectOriginalImage = (JSONObject) jsonObject.get("originalimage");
            imageURL= (String) jsonObjectOriginalImage.get("source");


            //get text
            extractText = (String)jsonObject.get("extract");
            res= new WikipediaApiResponse(displayTitle, extractText, imageURL);
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

}
