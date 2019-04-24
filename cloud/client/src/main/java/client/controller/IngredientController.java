package client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class IngredientController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClient;

    @GetMapping("/getText")
    public ResponseEntity<String> getIngredient() {
        return new ResponseEntity<String>("Test message", HttpStatus.OK);
    }

    //using getting method on service itself for testing perspectives
    @GetMapping("/makeTest")
    public ResponseEntity<String> makeTest() {
        String forObject = restTemplate.getForObject("http://ingredient-service/getText", String.class);
        return new ResponseEntity<String>(forObject, HttpStatus.OK);
    }

    @GetMapping("/makeReactiveTest")
    public Mono<String> makeReactiveTest() {
        Mono<String> stringMono = webClient.build()
                .get()
                .uri("http://ingredient-service/getText")
                .retrieve().bodyToMono(String.class);
        return stringMono;
    }
}
