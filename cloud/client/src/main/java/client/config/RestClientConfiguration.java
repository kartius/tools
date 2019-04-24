package client.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableFeignClients
public class RestClientConfiguration {

    @FeignClient("ingredient-service")
    public interface IngredienClient {

        @GetMapping("/makeTest")
        String makeTest();
    }
}
