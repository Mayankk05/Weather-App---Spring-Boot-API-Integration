package api.Integration.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@CrossOrigin(origins = "*")
@RestController
public class ApiController {

    private final WebClient webClient;

    // Constructor to initialize WebClient
    public ApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String city) {
        String apiKey = "34cb340ca283a10d3af5adc8b209061a"; // Replace with your OpenWeatherMap API key

        try {
            String response = webClient.get()
                    .uri("/weather?q=" + city + "&appid=" + apiKey + "&units=metric")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}
