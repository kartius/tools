package com.example.contract.consumer;

import com.example.common.model.Person;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractRestClientApplicationTest {

	@Rule
	public StubRunnerRule stubRunnerRule = new StubRunnerRule()
		.downloadStub("com.example.contract", "provider", "0.0.1-SNAPSHOT", "stubs")
		.withPort(8200)
		.stubsMode(StubRunnerProperties.StubsMode.LOCAL);

	@Test
	public void get_person_from_service_contract() throws InterruptedException {
		// given:
		RestTemplate restTemplate = new RestTemplate();
		
		// when:
		ResponseEntity<Person> personResponseEntity = restTemplate.getForEntity("http://localhost:8200/person/1", Person.class);

		// then:
		BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
		BDDAssertions.then(personResponseEntity.getBody().getId()).isEqualTo(1l);
		BDDAssertions.then(personResponseEntity.getBody().getName()).isEqualTo("foo");
		BDDAssertions.then(personResponseEntity.getBody().getSurname()).isEqualTo("bee");
		
	}

    @Test
    public void get_all_persons_from_service_contract() {
        // given:
        RestTemplate restTemplate = new RestTemplate();

        // when:
        ResponseEntity<Map> personResponseEntity = restTemplate.getForEntity("http://localhost:8200/persons", Map.class);

        // then:
		List<Person> persons = (List<Person>) personResponseEntity.getBody().get("persons");
		BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(persons.size()).isEqualTo(2);

    }
}
