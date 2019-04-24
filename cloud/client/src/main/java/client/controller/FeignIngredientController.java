package client.controller;


import static client.config.RestClientConfiguration.IngredienClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignIngredientController {

    @Autowired
    private IngredienClient ingredienClient;

    @GetMapping("/makeTest")
    public ResponseEntity<String> makeTest() {
        String result = ingredienClient.makeTest();
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

}
